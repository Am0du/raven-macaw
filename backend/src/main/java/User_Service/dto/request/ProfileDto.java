package User_Service.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileDto {

    private String firstName;

    private String lastName;

    private String profilePicture;

    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String gender;

    @Size(min = 2, max = 2, message = "Country code must be exactly 2 characters long.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country code must consist of two uppercase letters.")
    private String countryCode;
}
