package User_Service.utils;

import User_Service.entity.Role;
import User_Service.entity.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration}")
    private long expirationMs;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    private Date expirationDate(long expirationMs) {
        return Date.from(
                LocalDateTime.now()
                        .plusMinutes(expirationMs / 60000)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public  <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("publicId", user.getPublicId());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("isActive", user.isActive());
        claims.put("roles", user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate(expirationMs))
                .signWith(key())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public UserDetails getUserDetails(String token){
        Claims claims = extractClaims(token, Function.identity());
        User user = new User();
        user.setPublicId((String) claims.get("publicId"));
        user.setEmail((String) claims.get("email"));
        user.setFirstName((String) claims.get("firstName"));
        user.setLastName((String) claims.get("lastName"));
        user.setActive((boolean) claims.get("isActive"));
        claims.put("isActive", user.isActive());

        Set<Role> userRole = new HashSet<>();
        List<String> roles = (List<String>) claims.get("roles");

        roles.forEach(roleName ->{
            Role role = new Role();
            role.setRole(roleName);
            userRole.add(role);
        });
        user.setRoles(userRole);
        return (UserDetails) user;
    }


    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
}
