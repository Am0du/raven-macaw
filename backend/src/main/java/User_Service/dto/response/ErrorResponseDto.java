package User_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record ErrorResponseDto(String error, String message,boolean success, int statusCode) {}
