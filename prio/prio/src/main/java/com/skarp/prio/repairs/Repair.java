package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Repair {

    @Id
    private String id;

    private Product product;
    private RepairState state;
    private Date startDate;
    private Date endDate;
    private Date pausedAt;
    private Date resumedAt;
    private Long pausedTime = 0L;
    private Long repairTime = 0L;

    public Repair(Product product) {
        this.state = RepairState.ON_GOING;
        this.startDate = new Date();
        this.product = product;

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

    public String getId() {
        return this.id;
    }
}
