package com.skarp.prio.writeoffticket;

import java.util.ArrayList;
import java.util.List;

public class WriteOffTicketForm {

    private String reason;
    private List<String> markedParts = new ArrayList<>();

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getMarkedParts() {
        return markedParts;
    }

    public void addMarkedParts(List<String> parts) {

        markedParts.addAll(parts);
    }

}
