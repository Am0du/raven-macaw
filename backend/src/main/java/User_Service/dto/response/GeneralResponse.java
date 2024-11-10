package User_Service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GeneralResponse<T> {
    private int statusCode;
    private boolean success;
    private T data;
}
