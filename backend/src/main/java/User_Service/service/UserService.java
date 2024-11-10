package User_Service.service;

import User_Service.dto.request.ProfileDto;
import User_Service.dto.request.SignupDto;
import User_Service.dto.response.GeneralResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    GeneralResponse<?> registerUser(SignupDto userRequestDto);
}
