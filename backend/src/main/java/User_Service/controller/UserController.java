package User_Service.controller;

import User_Service.dto.request.ProfileDto;
import User_Service.dto.request.SignupDto;
import User_Service.dto.response.GeneralResponse;
import User_Service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse<?>> registerUser(@Valid @RequestBody SignupDto signupDto){
        return new ResponseEntity<GeneralResponse<?>>(userService.registerUser(signupDto), HttpStatus.CREATED);
    }
}
