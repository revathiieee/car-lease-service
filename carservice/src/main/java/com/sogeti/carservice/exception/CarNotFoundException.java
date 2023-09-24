package com.sogeti.carservice.exception;

/**
 * Car not found exception
 *
 * This class is used to throw exception when car is not found
 * @Author: revathi
 */
public class CarNotFoundException extends RuntimeException {

      /**
      * Constructor
      * @param message
      */
      public CarNotFoundException(String message) {
          super(message);
      }
}
