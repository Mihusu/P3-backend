package com.skarp.prio.repairs;

public class IncompatibleSparepartTypeException extends RuntimeException {

    String msg;

    IncompatibleSparepartTypeException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
