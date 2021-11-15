package com.skarp.prio.writeoffticket;

import com.skarp.prio.Technician;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;

import java.util.Date;

public class WriteOffTicket {

    private Product product;
    private Technician technician;
    private Date creationDate;
    private Date approvalDate;
    private WriteOffTicketState state;

    public WriteOffTicket(Product product, Technician technician){
        this.product = product;
        this.technician = technician;
        this.creationDate = new Date();
        this.state = WriteOffTicketState.AWAITING;

        product.setState(ProductState.IN_WRITEOFF);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getState() {
        return this.state.toString();
    }

    public void approve(){
        this.state = WriteOffTicketState.APPROVED;
        this.approvalDate = new Date();

        product.setState(ProductState.WRITTEN_OFF);
    }
    public void decline(){
        this.state = WriteOffTicketState.DECLINED;

        product.setState(ProductState.DEFECTIVE);
    }

    public Date getApprovalDate() {

        if (this.state != WriteOffTicketState.APPROVED) {
            return null;
        }

        return this.approvalDate;
    }
}
