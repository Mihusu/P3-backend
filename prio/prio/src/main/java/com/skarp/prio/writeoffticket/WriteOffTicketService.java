package com.skarp.prio.writeoffticket;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WriteOffTicketService {

    WriteOffTicket createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id);
    List<WriteOffTicket> getAllWriteOffTickets();
    void approveWriteOffTicket();
    void declineWriteOffTicket();

}
