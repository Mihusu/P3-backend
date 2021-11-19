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

    public Date getStartDate() {
        return this.startDate;
    }

    public long getRepairTimeInMilliSeconds() {
        endDate = new Date();
        return this.endDate.getTime() - this.startDate.getTime();

    }

    public String getId() {
        return this.id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void pauseRepair() {

        if (!this.state.equals(RepairState.ON_GOING)) {
            throw new IllegalRepairOperationException("Repair must be on-going before a pause");
        }

        this.state = RepairState.PAUSED;
        this.pausedAt = new Date();
    }

    public void resumeRepair() {

        if (!this.state.equals(RepairState.PAUSED)) {
            throw new IllegalRepairOperationException("Repair must be paused before resume");
        }

        this.state = RepairState.ON_GOING;
        this.resumedAt = new Date();
        this.pausedTime += this.resumedAt.getTime() - this.pausedAt.getTime();

    }

    public void finishRepair() {

        if (!this.state.equals(RepairState.ON_GOING)) {
            throw new IllegalRepairOperationException("Repair must be on going before it can be finished");
        }

        this.state = RepairState.FINISHED;
        this.endDate = new Date();
        product.setState(ProductState.REPAIRED);

    }
}
