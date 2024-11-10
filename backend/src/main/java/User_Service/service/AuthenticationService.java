package User_Service.service;

import User_Service.dto.request.LoginDto;
import User_Service.dto.response.AuthDto;

public interface AuthenticationService {
    AuthDto<?> login(LoginDto loginDto);
}
