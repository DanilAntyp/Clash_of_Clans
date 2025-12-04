package com.example.clashofclans.exceptions.unitExceptions;

public class UnitPersistencyException extends UnitException {
    public UnitPersistencyException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
