package com.example.ratesplatform.shared.config.error;

import com.example.ratesplatform.shared.common.dtos.ServiceErrorDto;
import com.example.ratesplatform.shared.config.error.ServiceException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Map;


/**
 * Class to handle the exceptions thrown in the application.
 * Each exception handled will be mapped to an HTTP response
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method to handle exceptions to type ServiceException.
     * This type of exception is generated inside the application
     *
     * @param ex exception produced containing info about the problem
     * @return entity with status defined by the exception and the error body
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(final ServiceException ex) {
        final HttpStatus httpStatus = Arrays.stream(HttpStatus.values())
                .filter(status -> String.valueOf(status.value()).equals(ex.getCode()))
                .findAny()
                .orElse(HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("Something went wrong. Error: {}", new Gson().toJson(ex.getErrors()));
        return buildResponseEntity(httpStatus, ex.getMessage(), ex.getErrors());
    }

    /**
     * Builds the application response
     *
     * @param status       status for the response
     * @param message      error message
     * @param errorDetails errors happened
     * @return application response
     */
    private ResponseEntity<Object> buildResponseEntity(
            final HttpStatus status,
            final String message,
            final Map<String, Object> errorDetails
    ) {
        final ServiceErrorDto errorResponse = new ServiceErrorDto(status.value(), message, errorDetails);
        return new ResponseEntity<>(errorResponse, status);
    }
}
