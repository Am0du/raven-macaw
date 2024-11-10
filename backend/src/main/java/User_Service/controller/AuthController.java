package User_Service.controller;

import User_Service.dto.request.LoginDto;
import User_Service.dto.response.AuthDto;
import User_Service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthDto<?>> login(@Valid @RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authenticationService.login(loginDto), HttpStatus.OK);
    }
}
