package User_Service.service.impl;

import User_Service.dto.request.SignupDto;
import User_Service.dto.response.*;
import User_Service.entity.User;
import User_Service.exception.BadRequestException;
import User_Service.repository.UserRepository;
import User_Service.service.RoleService;
import User_Service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    @Transactional
    @Override
    public GeneralResponse<?> registerUser(@Valid SignupDto signupDto) {

        if(userRepository.existsByEmail(signupDto.getEmail()))
            throw new BadRequestException("Email already exist");

        User user = User.builder()
                .email(signupDto.getEmail())
                .firstName(signupDto.getFirstName())
                .lastName(signupDto.getLastName())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .build();

        user.addRole(roleService.getRoleByName("ROLE_USER"));

        User savedUser = userRepository.save(user);

        return GeneralResponse.builder()
                .success(true)
                .statusCode(HttpStatus.CREATED.value())
                .data(UserResponseDto.builder()
                        .email(savedUser.getEmail())
                        .id(savedUser.getPublicId())
                        .firstName(savedUser.getFirstName())
                        .lastName(savedUser.getLastName())
                        .build())
                .build();
    }
}