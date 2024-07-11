package com.example.ratesplatform.shared.common.dtos;


import com.example.ratesplatform.shared.common.utils.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Map;


/**
 * Class to map exceptions thrown inside the application.
 * This class is used as a response model in case something went wrong
 */
@Getter
@Schema(name = "ErrorResponse")
public class ServiceErrorDto {

    // Node names in the JSON
    private static final String ERRORS_NODE = "errors";
    private static final String ERROR_DETAILS_NODE = "errorDetails";
    private static final String TIMESTAMP_NODE = "timestamp";

    @JsonProperty(Constants.CODE)
    private final int code;

    @JsonProperty(Constants.MESSAGE)
    private final String message;

    @JsonProperty(ERROR_DETAILS_NODE)
    private final Map<String, Object> errorDetails;

    /**
     * Constructor with only the code and exception message
     * @param code Code associated with the exception
     * @param message Exception message
     */
    public ServiceErrorDto(final int code, final String message) {
        this(code, message, null);
    }

    /**
     * Constructor with all parameters
     * @param code Code associated with the exception
     * @param message Exception message
     * @param errorDetails Map containing details about each error
     *                      that has made the exception to be thrown
     */
    public ServiceErrorDto(final int code, final String message, final Map<String, Object> errorDetails) {
        this.code = code;
        this.message = message;
        this.errorDetails = errorDetails;
    }
}
