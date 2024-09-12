package com.aalto.paycraft.exception;


import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.dto.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.aalto.paycraft.constant.PayCraftConstant.*;

@Slf4j @ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /* handles all exceptions */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmployerProfileAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleEmployerProfileAlreadyExistException(EmployerProfileAlreadyExists exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployerProfileNotFound.class)
    public ResponseEntity<ErrorResponseDto> handleEmployerProfileNotFoundException(EmployerProfileNotFound exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        log.error("{}:{}:{}", headers.getOrigin(), status.value(), request.getHeaderNames());

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put( error.getField() , error.getDefaultMessage());
        });

        DefaultApiResponse<Map<String, String>> response = new DefaultApiResponse<>();
        log.error("Validation Failed: ({})", ex.getMessage());

        response.setStatusCode(STATUS_400);
        response.setStatusMessage("Validation Failed");
        response.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredJWTException(ExpiredJwtException ex, WebRequest webRequest)
    {
        log.warn("Expired JWT Exception: {}", ex.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                "JWT Expired: Prompt user to Login or Refresh Token",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtSignatureExceptions(SignatureException ex, WebRequest webRequest)
    {
        log.error("Signature Exception {}", ex.getMessage());
        ErrorResponseDto response = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.UNAUTHORIZED,
                "JWT Signature Compromised: Prompt user to Login or Refresh Token",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}