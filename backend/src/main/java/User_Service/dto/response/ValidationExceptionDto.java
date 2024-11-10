package User_Service.dto.response;

import java.util.Map;

public record ValidationExceptionDto(int statusCode, String error,boolean success, Map<String, String> detail){}

