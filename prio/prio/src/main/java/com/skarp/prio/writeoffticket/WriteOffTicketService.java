package com.skarp.prio.writeoffticket;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WriteOffTicketService {

    public void createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) throws Exception;
    List<WriteOffTicket> getAllWriteOffTickets();
    void approveWriteOffTicket();
    void declineWriteOffTicket();

}
