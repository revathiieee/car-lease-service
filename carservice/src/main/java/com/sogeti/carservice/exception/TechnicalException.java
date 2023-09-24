package com.sogeti.carservice.exception;

/**
 * Technical exception
 *
 * This exception is thrown when there is an issue with the service
 * @Author: revathi
 */
public class TechnicalException extends RuntimeException{
        /**
         * Constructor
         * @param message
         */
        public TechnicalException(String message) {
            super(message);
        }
}
