package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Repair {

    @Id
    private String id;

    private RepairState state;
    private Date startDate;
    private Date endDate;
    private Date pausedAt;
    private Date resumedAt;
    private Long pausedTime = 0L;
    private Long repairTime = 0L;
    private Product product; // The (in store) product number of the product which is associated to this repair

    public Repair(Product product) {
        this.product = product;
        this.state = RepairState.ON_GOING;
        this.startDate = new Date();

        product.setState(ProductState.IN_REPAIR);
    }

    public RepairState getState() {
        return this.state;
    }

    public void pauseRepair() {
        this.state = RepairState.PAUSED;
        this.pausedAt = new Date();
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public long getRepairTimeInMilliSeconds() {
        endDate = new Date();
        return this.endDate.getTime() - this.startDate.getTime();

    }

    public void resumeRepair() {

        if (this.state.equals(RepairState.PAUSED)){
            this.state = RepairState.ON_GOING;
            this.resumedAt = new Date();
            this.pausedTime += this.resumedAt.getTime() - this.pausedAt.getTime();
        }
    }

    public Product getProduct(){
        return this.product;
    }
}
