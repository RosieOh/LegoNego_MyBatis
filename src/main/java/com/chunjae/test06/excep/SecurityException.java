package com.chunjae.test06.excep;

public class SecurityException extends Exception {

    private static final long serialVersionUID = 1L;

    public SecurityException() {
        super();
    }

    public SecurityException(String message) {
        super(message);
    }
}