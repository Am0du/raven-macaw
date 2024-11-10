package User_Service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {

    @NotBlank(message = "email cannot be null, empty or whitespace")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "password cannot be null, empty or whitespace")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter," +
                    " one uppercase letter, and one special character")
    @Size(min = 8, max = 30, message = "password must be at least 8 characters long")
    private String password;
}
