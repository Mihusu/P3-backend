package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The {@code RepairServiceImpl} class is responsible for implementing the logic to the methods provided by the
 * {@link RepairService} interface. The {@code RepairServiceImpl} uses the {@code @Service} annotation, as it provides
 * services for the {@link RepairController}.
 *
 * @author Team Skarp
 * @see RepairService
 * @see RepairController
 * @see RepairRepository
 * @see ProductRepository
 * @see SparePartRepository
 * @see Repair
 * @see Product
 * @see com.skarp.prio.Technician
 * @see ProductState
 * @see RepairState
 * @see SparePart
 */

@Service
public class RepairServiceImpl implements RepairService {


    /**
     * The repositories are {@code @Autowired}, as the methods need to access objects in several collections in the
     * database. We use the {@code @Autowired} annotation to establish a connection to {@link RepairRepository},
     * {@link ProductRepository}, {@link SparePartRepository}.
     */
    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SparePartRepository sparePartRepository;

    @Autowired
    MongoOperations operations;

    /**
     * {@code getRepairList} is a get method for the {@link RepairController}.
     * @param sortBy, an optional {@code String} which allows sorting of the returned {@code List} of {@code Repair}
     *                objects.
     * @param LIMIT, an optional {@code String} which limits the amount of {@code Repair} objects being returned.
     * @return a {@code List} of {@code Repair} objects based on the {@code Query} made from the parameters,
     * using the MongoOperations {@code find} method.
     */
    @Override
    public List<Repair> getRepairList(String sortBy, String LIMIT) {

        Query repairQuery = new Query();
        if (sortBy != null) {repairQuery.with(Sort.by(Sort.Direction.DESC, sortBy));}
        return operations.find(repairQuery, Repair.class);

    }

    /**
     * The {@code createRepair} method is used to start a new {@link Repair} on a {@link Product} for an optional
     * {@link com.skarp.prio.Technician}. It finds the {@code Product} in the {@code productRepository} using the
     * {@code findByID} method, if the product doesn't exist it throws a {@code NoSuchElementException}.
     * The {@link ProductState} is set to {@code IN_REPAIR} for the provided product and both the {@code Product} and
     * the {@code Repair} is saved in the MongoDB repository with the {@code save} method.
     * @param prod_id a required {@code String} referring to the product for which the {@code Repair} is started.
     * @param tech_id an optional {@code String} referring to the technician who initialized the repair.
     * @return a {@code uriComponents} object with the path of the newly created repair using the {@code getId} method
     * from {@link Repair} to {@code buildAndExpand} the URI with the {@code Id} of this specific repair.
     */
    @Override
    public Repair createRepair(String prod_id, String tech_id) {

        Product product;
        Repair repair;

        product = productRepository.findById(prod_id).orElseThrow();

        repair = new Repair(product);
        product.setState(ProductState.IN_REPAIR);

        productRepository.save(product);
        return repairRepository.save(repair);

        //Builds URI path
        //UriComponents uriComponents = uriComponentsBuilder.path("/repairs/" + repair.getId()).buildAndExpand(repair.getId());

        //return uriComponents.toUri();

    }

    /**
     * Get method for finding a single repair by its {@code Id}.
     * @param id a {@code PathVariable String} providing the {@code Id} for the {@code Repair}.
     * @return a {@code Repair} object, if it finds one in the {@code repairRepository} with the provided {@code Id},
     * otherwise throws a {@code NoSuchElementException}.
     */
    @Override
    public Repair getRepairByID(String id) {

        if (repairRepository.findById(id).isEmpty()) {
            String msg = "Repair not found with id: " + id;
            throw new NoSuchElementException(msg);
        }

        return repairRepository.findById(id).get();
    }

    /**
     * {@code pauseRepair} is a setter used to change the {@link RepairState} of a {@code Repair} to {@code "PAUSED"}.
     * The method finds a repair object in the {@code repairRepository} with the {@code Id} provided as the
     * {@code pathVariable}. If a {@code Repair} is found with the provided {@code Id}, it uses the {@code getState}
     * method from the {@link Repair} class, as only {@code Repair} objects in the {@code RepairState "ON_GOING"} can
     * be paused. When the {@code Repair} object is present and the {@code RepairState} is {@code "ON_GOING"}, the
     * state is set to {@code "PAUSED"} using the {@code setState} method and the {@code pausedAt} date is set using
     * the {@code setPausedAt} method. Lastly the new state of the repair is saved in the {@link RepairRepository}.
     * An {@code IllegalRepairOperationException} is thrown if the {@code RepairState} of the object is not
     * {@code "ON_GOING"}. A {@code NoSuchElementException} is thrown if there is no {@code Repair} found with the
     * provided {@code Id} in the {@code RepairRepository}.
     * @param id a {@code String} with the {@code Id} for the {@code Repair} object.
     */
    @Override
    public Repair pauseRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {
            Repair repairModel = repair.get();

            if (repairModel.getState().equals(RepairState.ON_GOING)) {

                repairModel.setState(RepairState.PAUSED);
                repairModel.setPausedAt(new Date());

                return repairRepository.save(repairModel);
            }
            throw new IllegalRepairOperationException("Repair must be on-going before a pause");

        }

        throw new NoSuchElementException("Repair not found with id: " + id);
    }

    /**
     * The {@code resumeRepair} method functions similarly to the {@link #pauseRepair(String id)} method,
     * but changes the {@code RepairState} of a {@code Repair} object which is in the {@code "PAUSED"} state to the
     * {@code "ON_GOING"} state.
     * @param id a {@code String} with the {@code Id} for the {@code Repair} object.
     */
    @Override
    public Repair resumeRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();

            if (repairModel.getState().equals(RepairState.PAUSED)) {

                repairModel.setState(RepairState.ON_GOING);
                repairModel.setPausedTime(new Date());

                return repairRepository.save(repairModel);
            }
            throw new IllegalRepairOperationException("Repair must be paused before it can be resumed");
        }
        throw new NoSuchElementException("Repair not found :(");
    }

    /**
     * The {@code cancelRepair} method functions similarly to {@link #pauseRepair(String id)}.
     * It cancels the {@code Repair} provided by the {@code PathVariable String Id}.
     * As a {@code Repair} can have {@code SpareParts} added to it with the {@link #addSparePart(String, String)}
     * method, these made available again by the method {@code getAddedSpareParts} making a list of added parts and
     * the {@code setState} method from {@link SparePart}, setting the state to {@code "AVAILABLE"} on each of them.
     * The {@code SpareParts} are disassociated with the {@code Repair} with the {@code removeSparePart} method.
     * @param id a {@code String} with the {@code Id} for the {@code Repair} object.
     */
    @Override
    public void cancelRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();
            Product productModel = repairModel.getProduct();

            if (!repairModel.getState().equals(RepairState.FINISHED)) {

                // Set all spare parts states to available
                repairModel.getAddedSpareParts().forEach((sparePart -> {
                    sparePart.setState(SparePartState.AVAILABLE);
                    repairModel.removeSparePart(sparePart);

                    sparePartRepository.save(sparePart);
                }));

                // Free the product under repair to be available and delete the repair entity.
                productModel.setState(ProductState.DEFECTIVE);

                productRepository.save(productModel);
                repairRepository.delete(repairModel);

                return;
            }
            throw new IllegalRepairOperationException("Repair can not be cancelled when finished");
        }
        throw new NoSuchElementException("Repair not found with id: " + id);
    }

    /**
     * The {@code finishRepair} method relies on many of the methods documented throughout {@link RepairServiceImpl}.
     * It takes a {@code Repair} in the {@code RepairState "ON_GOING"} and sets the state to {@code "FINISHED"}.
     * Then the {@code endDate} is set to the current date and the repair time is calculated using
     * {@code endDate, startDate} and {@code pausedTime}. The {@code costPrice} of the {@code Product} object being
     * repaired is updated with the {@code costPrice} of each {@code SparePart} used in the repair, and each
     * {@code SparePart} has its {@code SparePartState} set to {@code "CONSUMED"}.
     * An {@code IllegalRepairOperationException} is thrown if the provided {@link Repair} is not in the
     * {@code "ON_GOING"} state, and a {@code "NoSuchElementException} if no {@code Repair} is found with the
     * {@code PathVariable id}.
     * @param id a {@code String} with the {@code Id} for the {@code Repair} object.
     */
    @Override
    public void finishRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();
            Product productModel = repairModel.getProduct();

            if (repairModel.getState().equals(RepairState.ON_GOING)) {

                repairModel.setState(RepairState.FINISHED);
                repairModel.setEndDate(new Date());
                repairModel.setRepairTime(((repairModel.getEndDate().getTime() - repairModel.getPausedTime()) - repairModel.getStartDate().getTime()));

                productModel.setCostPrice(productModel.getCostPrice() + repairModel.getRepairCost());
                productModel.setState(ProductState.REPAIRED);

                repairModel.getAddedSpareParts().forEach((sparePart -> {
                    sparePart.setState(SparePartState.CONSUMED);
                    sparePartRepository.save(sparePart);
                }));

                productRepository.save(productModel);
                repairRepository.save(repairModel);
                return;
            }
            throw new IllegalRepairOperationException("Repair must be on-going before it can be finished");
        }
        throw new NoSuchElementException("Repair not found with id: " + id);
    }

    /**
     * The {@code addSparePart} method is used to apply a {@link SparePart} to a {@link Repair}.
     * The method adds a {@code SparePart} object to a {@code Repair} with the {@code addSparePart} method.
     * It sets the {@code SparePartState} to {@code "RESERVED"} such that it can not be used in another {@code Repair}.
     * Before adding the {@code SparePart} it checks whether the specific {@code SparePart} is already added.
     *
     * @param id a {@code String} for the {@code Id} of the {@code Repair} object.
     * @param sparepart_id a {@code String} for the {@code Id} of the {@code SparePart} object.
     */
    @Override
    public SparePart addSparePart(String id, String sparepart_id) {

        Optional<Repair> repair = repairRepository.findById(id);
        Optional<SparePart> sparePart = sparePartRepository.findById(sparepart_id);

        if (repair.isPresent() && sparePart.isPresent()) {

            Repair repairModel = repair.get();
            SparePart sparePartModel = sparePart.get();

                // Check if the repair already contains this spare part
                List<SparePart> duplicates = repairModel.getAddedSpareParts()
                        .stream().filter(part -> part.getPart_id().compareTo(sparePartModel.getPart_id()) == 0).toList();

                if (repairModel.getState() != RepairState.ON_GOING) {
                    throw new IllegalRepairOperationException("Spare part can not be added while repair is: " + repairModel.getState());
                }
                if (duplicates.isEmpty()) {
                    sparePartModel.setState(SparePartState.RESERVED);
                    repairModel.addSparePart(sparePartModel);

                    repairRepository.save(repairModel);
                    return sparePartRepository.save(sparePartModel);

                } else {
                    throw new IllegalRepairOperationException("Repair can not contain the same spare-part twice");
                }
            } else {
                throw new NoSuchElementException("Must add an existing SparePart to an existing Repair");
            }
    }

    /**
     * The {@code removeSparePart} method functions similarly to the {@link #addSparePart(String, String)} method and
     * exists because a {@code SparePart} can be faulty, requiring removal.
     * @param repairId a {@code String} for the {@code Id} of the {@code Repair} object.
     * @param sparepartId a {@code String} for the {@code Id} of the {@code SparePart} object.
     */
    @Override
    public SparePart removeSparePart(String repairId, String sparepartId) {

        Optional<Repair> repair = repairRepository.findById(repairId);
        Optional<SparePart> sparePart = sparePartRepository.findById(sparepartId);

        if (repair.isPresent() && sparePart.isPresent()) {

            Repair repairModel = repair.get();
            SparePart sparePartModel = sparePart.get();

            if (repairModel.getState() != RepairState.ON_GOING) {
                throw new IllegalRepairOperationException("Spare part can not be removed while repair is: " + repairModel.getState());
            }

            sparePartModel.setState(SparePartState.AVAILABLE);
            repairModel.removeSparePart(sparePartModel);

            repairRepository.save(repairModel);
            return sparePartRepository.save(sparePartModel);
        }
        throw new NoSuchElementException("The current repair does not contain the requested spare part");
    }
}
