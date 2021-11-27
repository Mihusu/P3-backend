package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.SparePart;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<SparePart> spareParts = new ArrayList<>();

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

    public void setState(RepairState state) {
        this.state = state;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPausedAt(Date pausedAt) {
        this.pausedAt = pausedAt;
    }

    public void setResumedAt(Date resumedAt) {

        this.resumedAt = resumedAt;
        this.pausedTime += this.resumedAt.getTime() - this.pausedAt.getTime();
    }

    public void finishRepair() {

        double addedProductCost = 0;
        double currentProductCost = this.product.getCostPrice();

        if (!this.state.equals(RepairState.ON_GOING)) {
            throw new IllegalRepairOperationException("Repair must be on going before it can be finished");
        }

        //Update product cost
        for (SparePart sp : this.spareParts) {
            addedProductCost += sp.getCostPrice();
        }

        this.product.setCostPrice(currentProductCost + addedProductCost);

        this.state = RepairState.FINISHED;
        this.endDate = new Date();
        product.setState(ProductState.REPAIRED);

    }

    public void addSparePart(SparePart sparePart) {

        this.spareParts.add(sparePart);
    }

    public List<SparePart> getAddedSpareParts() {
        return this.spareParts;
    }

    public double getRepairCost() {

        double repairCost = 0;

        for (SparePart sp : this.spareParts) {
            repairCost += sp.getCostPrice();
        }

        return repairCost;
    }

    public void removeSparePart(SparePart sparePart) {
        // Filter out the received spare part from spare parts list
        List<SparePart> filtered_parts = this.spareParts.stream()
                .filter(sp -> !sp.getPart_id().equals(sparePart.getPart_id()))
                .collect(Collectors.toList());
        this.spareParts = filtered_parts;
    }
}
