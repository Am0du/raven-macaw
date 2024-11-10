package User_Service.config.security;

import User_Service.entity.User;
import User_Service.exception.BadRequestException;
import User_Service.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = (User) userDetails;

        if(user.isLocked()) {
            log.warn("User [{}] account is locked", username);
            throw new BadRequestException("Invalid Credential");
        }

        if(user.isDeleted())
            throw new BadRequestException("Invalid Credential");

        if(!user.isActive()) {
            log.warn("User [{}] account is deactivated", username);
            throw new UnauthorizedException("invalid Credential");
        }

        if (!passwordEncoder.matches(pwd, userDetails.getPassword())) {
            log.warn("User [{}] login failed", username);
            throw new BadRequestException("Invalid Credentials.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
