package com.example.demo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.javapoet.ClassName;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        String location = stringify(exception.getStackTrace()[0]);
        String message = stringify(exception);
        log.warn("Location : {} MethodArgumentNotValidException : [{}]",location, message);
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
        String location = stringify(exception.getStackTrace()[0]);
        String message = stringify(exception);
        log.warn("Location : {} ConstraintViolationException : [{}]",location, message);
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException exception) {
        String location = stringify(exception.getStackTrace()[0]);
        log.warn("Location : {} ResponseStatusException : [{}]", location, exception.getReason());
        return build(HttpStatus.valueOf(exception.getStatusCode().value()), exception.getReason());
    }

    private String stringify(MethodArgumentNotValidException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMessageBuilder.append(fieldError.getField()).append(": ");
            errorMessageBuilder.append(fieldError.getDefaultMessage()).append(", ");
        }
        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        return errorMessageBuilder.toString();
    }

    private String stringify(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);

        return String.format("[%s.%s(%s:%d)]",
                className,
                stackTraceElement.getMethodName(),
                stackTraceElement.getFileName(),
                stackTraceElement.getLineNumber()
        );
    }


    private String stringify(ConstraintViolationException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        for (ConstraintViolation<?> fieldError : exception.getConstraintViolations()) {
            String propertyPath = fieldError.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
            errorMessageBuilder.append(fieldName).append(": ");
            errorMessageBuilder.append(fieldError.getMessage()).append(", ");
        }

        errorMessageBuilder.deleteCharAt(errorMessageBuilder.length() - 2);
        return errorMessageBuilder.toString();
    }

    private ProblemDetail build(HttpStatus status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        return problemDetail;
    }
}