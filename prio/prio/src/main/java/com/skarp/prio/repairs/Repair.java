package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;

import java.util.Date;

public class Repair {

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
}
