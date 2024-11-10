package User_Service.exception;

import User_Service.dto.response.ErrorResponseDto;
import User_Service.dto.response.ValidationExceptionDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationExceptionDto handleValidationException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        for (FieldError error: ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ValidationExceptionDto(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),false, errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleUnauthorizedException(UnauthorizedException ex){
        return setResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleFobiddenException(ForbiddenException ex){
        return setResponse(HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleNotFoundException(NotFoundException ex){
        return setResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBadRequestException(BadRequestException ex){
        return setResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler({ SignatureException.class, MalformedJwtException.class, UnsupportedJwtException.class, ExpiredJwtException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleJwtException(Exception ex) {
        return setResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Invalid or expired token." , HttpStatus.UNAUTHORIZED.value());
    }

    private ErrorResponseDto setResponse(String error, String message, int statusCode){
        return new ErrorResponseDto(error, message,false, statusCode);
    }
}
