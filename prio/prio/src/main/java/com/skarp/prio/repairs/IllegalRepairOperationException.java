package com.skarp.prio.repairs;

public class IllegalRepairOperationException extends RuntimeException {

    String msg;

    IllegalRepairOperationException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
