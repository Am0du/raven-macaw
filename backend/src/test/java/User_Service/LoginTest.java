package User_Service;

import User_Service.dto.request.LoginDto;
import User_Service.dto.response.AuthDto;
import User_Service.entity.Role;
import User_Service.entity.User;
import User_Service.service.impl.AuthenticationServiceImpl;
import User_Service.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private LoginDto loginDto;
    private User user;


    @BeforeEach
    public void setup(){
        loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password");

        Role role = new Role();
        role.setRole("ROLE_USER");

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("test@example.com");
        user.addRole(role);

    }

    @Test
    void testLogin(){

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        when(authenticationManager.authenticate(authToken))
                .thenReturn(authentication);
        when(authentication.getPrincipal())
                .thenReturn(user);
        when(jwtUtils.generateAccessToken(user))
                .thenReturn("mockToken");

        AuthDto<?> result = authenticationService.login(loginDto);

        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        assertEquals("mockToken", result.getAccessToken());
        assertTrue(result.isSuccess());
    }
}
