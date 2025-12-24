package java19.excepion.handler;

import java19.dto.exceptionDto.ExceptionResponse;
import java19.excepion.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handlerAlreadyExistsException(AlreadyExistsException e) {
        return ExceptionResponse.builder().status(HttpStatus.CONFLICT).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerBadRequestException(BadRequestException e) {
        return ExceptionResponse.builder().status(HttpStatus.BAD_REQUEST).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handlerBadCredentialsException(BadCredentialsException e) {
        return ExceptionResponse.builder().status(HttpStatus.UNAUTHORIZED).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ExceptionResponse handlerNoSuchElementException(NoSuchElementException e) {
        return ExceptionResponse.builder().status(HttpStatus.NOT_FOUND).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return ExceptionResponse.builder().status(HttpStatus.NOT_FOUND).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(AccessIsDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //403
    public ExceptionResponse handlerAccessIsDeniedException(AccessIsDeniedException e) {
        return ExceptionResponse.builder().status(HttpStatus.FORBIDDEN).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }

    @ExceptionHandler(UserMismatchException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handelUserMismatchException(UserMismatchException e) {
        return ExceptionResponse.builder().status(HttpStatus.FORBIDDEN).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }


    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerUsernameNotFoundException(UsernameNotFoundException e) {
        return ExceptionResponse.builder().status(HttpStatus.NOT_FOUND).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();

    }

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handlerJwtAuthenticationException(JwtAuthenticationException e) {
        return ExceptionResponse.builder().status(HttpStatus.UNAUTHORIZED).exceptionName(e.getClass().getSimpleName()).message(e.getMessage()).build();
    }
}
