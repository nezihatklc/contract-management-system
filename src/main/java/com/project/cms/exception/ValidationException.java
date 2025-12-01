package com.project.cms.exception;

// "extends Exception" diyerek bunun bir Hata Sınıfı olduğunu belirtiyoruz.
public class ValidationException extends Exception {
    
    public ValidationException(String message) {
        super(message);
    }
}
