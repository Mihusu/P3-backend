package com.skarp.prio.writeoffticket;

import com.skarp.prio.Technician;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class WriteOffTicket {

    @Id
    private String id;
    private String productId; // Id of product for write-off
    private String reason; //Reason for making the write-off
    private String technicianName; // name of technician initiating the write-off
    private Date creationDate;
    private Date approvalDate;
    private WriteOffTicketState state;
    //private String approvedByManagerName;

    public WriteOffTicket(Product product, String technicianName){
        this.productId = product.getProductId();
        this.technicianName = technicianName;
        this.creationDate = new Date();
        this.state = WriteOffTicketState.AWAITING;
        product.setState(ProductState.IN_WRITEOFF);
    }

    public WriteOffTicketState getState() {
        return this.state;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public Date getApprovalDate() {
        if (this.state != WriteOffTicketState.APPROVED) {
            return null;
        }
        return this.approvalDate;
    }

    /* //Todo: should this be in a service?
        public void approve(){
            this.state = WriteOffTicketState.APPROVED;
            this.approvalDate = new Date();

            product.setState(ProductState.WRITTEN_OFF);
        }

     */
    /*  //Todo: should this be in a service?

    public void decline(){
        this.state = WriteOffTicketState.DECLINED;

        productId.setState(ProductState.DEFECTIVE);
    }
     */

    public String getId() {
        return id;
    }
}
