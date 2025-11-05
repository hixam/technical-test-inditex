package com.hicham.technicaltestinditex.application.exception;

/**
 * Exception thrown when no applicable price is found for the given criteria.
 */
public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

