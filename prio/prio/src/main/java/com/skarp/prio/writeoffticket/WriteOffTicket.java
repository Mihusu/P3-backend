package com.skarp.prio.writeoffticket;

import com.skarp.prio.Technician;
import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.SparePart;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteOffTicket {

    @Id
    private String id;
    private List<SparePart> sparePartList = new ArrayList<>();
    private Product product;
    private String productId;       // Id of product for write-off
    private String reason;          //Reason for making the write-off
    private String technicianName;  // name of technician initiating the write-off
    private Date creationDate;
    private Date approvalDate;
    private WriteOffTicketState state;
    //private String approvedByManagerName;

    public WriteOffTicket(Product product, String technicianName){
        this.product = product;
        this.productId = product.getProductId();
        this.technicianName = technicianName;
        this.creationDate = new Date();
        this.state = WriteOffTicketState.AWAITING;
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

    public void addReason(String reason) {
        this.reason = reason;
    }

    public String getId() {
        return this.id;
    }

    public Product getProduct() {
        return this.product;
    }
/*
    public List<SparePart> getSparePartList() {
        return sparePartList;

    }

    public void setSparePartList(List<SparePart> sparePartList) {
        this.SparePartList = sparePartList;
    }


    public Product getProduct() {
        return product;
    }

    public void addSparePart(SparePart sparePart) {}

 */

}
