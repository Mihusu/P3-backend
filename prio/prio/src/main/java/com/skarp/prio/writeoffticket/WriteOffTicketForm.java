package com.skarp.prio.writeoffticket;


import java.util.ArrayList;
import java.util.List;

public class WriteOffTicketForm {

    String reason;
    List<String> markedParts;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getMarkedParts() {
        return markedParts;
    }

}
