package com.sogeti.leaseservice.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.sogeti.leaseservice.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  Customer Controller Advice
 *
 *  This class is used to handle all the exceptions thrown by the customer controller
 *  @Author: revathi
 */
@RestControllerAdvice(assignableTypes = LeaseControllerAdvice.class)
@Slf4j
public class LeaseControllerAdvice {

    /**
     * Method to handle MethodArgumentNotValidException
     * @param ex
     * @return ErrorResponse
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        return new ErrorResponse("Request is Invalid");
    }

    /**
     * Method to handle Exception
     * @param ex
     * @return ErrorResponse
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception ex) {
        log.error("handleException: {}", ex.getMessage());
        return new ErrorResponse("Internal Server Error");
    }

    /**
     * Method to handle TechnicalException
     * @param ex
     * @return ErrorResponse
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ErrorResponse handleTechnicalException(Exception ex) {
        log.error("handleTechnicalException: {}", ex.getMessage());
        return new ErrorResponse("Internal Server Error");
    }
}
