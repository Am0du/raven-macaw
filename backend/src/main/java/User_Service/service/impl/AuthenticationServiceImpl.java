package User_Service.service.impl;

import User_Service.dto.request.LoginDto;
import User_Service.dto.response.AuthDto;
import User_Service.dto.response.UserResponseDto;
import User_Service.entity.User;
import User_Service.service.AuthenticationService;
import User_Service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public AuthDto<?> login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String token = jwtUtils.generateAccessToken(user);

        return AuthDto.builder()
                .success(true)
                .accessToken(token)
                .statusCode(HttpStatus.OK.value())
                .data(UserResponseDto.builder()
                        .id(user.getPublicId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .build()
                )
                .build();
    }
}
