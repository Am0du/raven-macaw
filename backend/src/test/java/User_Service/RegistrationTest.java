package User_Service;

import User_Service.dto.request.SignupDto;
import User_Service.dto.response.GeneralResponse;
import User_Service.dto.response.UserResponseDto;
import User_Service.entity.Role;
import User_Service.entity.User;
import User_Service.exception.BadRequestException;
import User_Service.repository.UserRepository;
import User_Service.service.RoleService;
import User_Service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private Thread thread;

    @InjectMocks
    private UserServiceImpl userService;

    private SignupDto signupDto;

    @BeforeEach
    public void setUp(){
        signupDto = new SignupDto();
        signupDto.setEmail("test@example.com");
        signupDto.setPassword("Password@1234)(*");
        signupDto.setFirstName("Johnny");
        signupDto.setLastName("Doe");
    }

    @Test
    public void successfullyRegisterUser() throws InterruptedException {

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");

        when(userRepository.existsByEmail(signupDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signupDto.getPassword())).thenReturn("encodedPassword");
        when(roleService.getRoleByName("ROLE_USER")).thenReturn(userRole);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        GeneralResponse<?> response = userService.registerUser(signupDto);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getData()).isInstanceOf(UserResponseDto.class);
    }

    @Test
    public void emailAlreadyExist() {

        when(userRepository.existsByEmail(signupDto.getEmail())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> userService.registerUser(signupDto));
        assertThat(exception.getMessage()).isEqualTo("Email already exist");
    }
}
