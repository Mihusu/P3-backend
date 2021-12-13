package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.user.User;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code Repair} class represents a repair existing in the Priotool application, which Blue City uses to
 * manage repairs. A {@code Repair} object contains information about the objectId, which {@link Product} object
 * the repair is made for, what state the repair is currently in, when the repair was started, when the repair
 * was ended, the amount of time the repair spent in the paused state, the total repair time for the repair,
 * as well as a list of the spareparts used during the repair.
 *
 * This is an example of the creation of a {@code Repair} object.
 *
 * <blockquote><pre>
 *     Repair repair = new Repair(Product);
 *     repair.setState(ON_GOING);
 *     repair.startDate = new Date();
 * </pre></blockquote>
 *
 * @author Team Skarp
 * @see Product
 * @see RepairState
 * @see SparePart
 * @see ProductState
 */

public class Repair {

    /**
     * Contains the ObjectId for the document in our database. */
    @Id
    private String id;

    /**
     * Contains the {@link Product} object which was used to initialize the repair.
     */
    private Product product;

    /**
     * Contains the {@link User} object which was used to initialize the repair.
     */
    private String technicianName;

    /**
     * Contains the current state of the repair.
     * The value is defined by the {@link RepairState} enum.
     */
    private RepairState state;

    /**
     * Contains the starting {@code Date} for the repair.
     */
    private Date startDate;

    /**
     * Contains the ending {@code Date} for the repair.
     */
    private Date endDate;

    /**
     * Contains the {@code Date} at which the repair was paused.
     */
    private Date pausedAt;

    /**
     * Contains the {@code Date} at which the repair was resumed.
     */
    private Date resumedAt;

    /**
     * Contains the total time the repair spent in the {@link RepairState} "PAUSED" state.
     */
    private Long pausedTime = 0L;

    /**
     * Contains the total repair time from start to finish.
     */
    private Long repairTime = 0L;

    /**
     * Contains a {@link SparePart} list of the SparePart objects used during the repair.
     */
    private List<SparePart> spareParts = new ArrayList<>();

    /**
     * Initializes a newly created {@code Repair} object with the given {@code Product} object.
     * The {@code RepairState} is set to {@code ON_GOING} and the start date is set to the current date.
     * The {@link ProductState} enum for the provided {@code Product} is set to {@code IN_REPAIR}.
     * @param product, a {@link Product} object which the repair will be started for.
     */
    public Repair(Product product) {
        this.state = RepairState.ON_GOING;
        this.startDate = new Date();
        this.product = product;
    }

    /**
     * Getter for the state of the repair.
     * @return an enum from {@link RepairState} specifying the current state of the repair.
     */
    public RepairState getState() {
        return this.state;
    }

    /**
     * Getter for the start date of the repair.
     * @return startDate, an {@code Date} object specifying the starting date for the repair.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Getter for the time the repair has spent paused.
     * @return pausedTime, a {@code Long} object specifying the time the repair has spent in the {@code PAUSED} state.
     */
    public Long getPausedTime() {
        return pausedTime;
    }

    /**
     * Getter for the end date of the repair.
     * @return endDate, a {@code Date} object specifying the date the repair is ended.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Getter for the product which was used to initialize the repair object.
     * @return product, a {@code Product} object from the {@link Product} class, specifying which product was used
     * to initialize the repair.
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Getter for the user which was used to initialize the repair object.
     * @return user, a {@code String} object from the {@link Repair} class, specifying who initiated the repair
     */
    public String getTechnicianName() {
        return this.technicianName;
    }


    /**
     * Getter for the Id of the repair.
     * @return id, a {@code String} object which refers to the document_id in the database.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Setter for the repair time of the {@code Repair}.
     * @param repairTime,a {@code Long} specifying the total amount of time spent repairing the {@code Product}.
     */
    public void setRepairTime(Long repairTime) {
        this.repairTime = repairTime;
    }

    /**
     * Setter for the technician name of the {@code Repair}.
     * @param tech_id,a {@code String} specifying the name of the technician who is initiated the repair on {@code Product}.
     */
    public void setTechnicianName(String tech_id) {
        this.technicianName = tech_id;
    }

    /**
     * Setter for the {@code RepairState} of the repair.
     * @param state, an enum from {@link RepairState} specifying the current state of the repair.
     */
    public void setState(RepairState state) {
        this.state = state;
    }

    /**
     * Setter for the end date of the repair.
     * @param endDate, a {@code Date} object specifying when the repair was finished.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Setter for a timestamp of when the repair was paused
     * @param pausedAt, a {@code Date} object specifying the moment the repair is paused.
     */
    public void setPausedAt(Date pausedAt) {
        this.pausedAt = pausedAt;
    }

    /**
     * Setter for the paused time of the repair.
     * @param resumedAt, a {@code Date} object specifying the time the repair was resumed.
     */
    public void setPausedTime(Date resumedAt) {

        this.resumedAt = resumedAt;
        this.pausedTime += this.resumedAt.getTime() - this.pausedAt.getTime();
    }

/*    public void finishRepair() {

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
        this.repairTime = this.endDate.getTime() - (this.startDate.getTime() - this.pausedTime);
        product.setState(ProductState.REPAIRED);
        //Todo find out if this is needed
    }*/

    /**
     * Setter for adding a sparepart to a product during a repair.
     * @param sparePart, a {@code sparePart} object from {@link SparePart} specifying which sparepart to be added
     *                   to the {@code List} of the repair.
     */
    public void addSparePart(SparePart sparePart) {

        this.spareParts.add(sparePart);
    }

    /**
     * Getter for the list of spareparts added to a product during a repair.
     * @return an {@code ArrayList} of {@link SparePart} objects, specifying which spareparts have been added
     * during the repair.
     */
    public List<SparePart> getAddedSpareParts() {
        return this.spareParts;
    }

    /**
     * Getter for the cost of the repair.
     * @return a {@code double} specifying the cost of the repair based on the value of {@link SparePart} objects used.
     */
    public double getRepairCost() {

        double repairCost = 0;

        for (SparePart sp : this.spareParts) {
            repairCost += sp.getCostPrice();
        }

        return repairCost;
    }

    /**
     * Setter for removing a sparepart which has been added to a repair.
     * It uses the {@code stream} interface and creates a new {@code List<>} of spareparts, applying the {@code filter}
     * method. For each object in the {@code List}, if the object is not equal to the {@code sparePart} given as
     * the parameter, the spareparts are accumulated to the new {@code List} using the {@code collect} method.
     * @param sparePart, a {@link SparePart} object, specifying which sparepart to remove.
     */
    public void removeSparePart(SparePart sparePart) {
        // Filter out the received spare part from spare parts list
        List<SparePart> filtered_parts = this.spareParts.stream()
                .filter(sp -> !sp.getPart_id().equals(sparePart.getPart_id()))
                .collect(Collectors.toList());
        this.spareParts = filtered_parts;
    }
}
