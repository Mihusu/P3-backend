package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SparePartRepository sparePartRepository;

    @Autowired
    MongoOperations operations;

    @Override
    public List<Repair> getRepairList(String sortBy, String LIMIT) {

        Query repairQuery = new Query();

        return operations.find(repairQuery, Repair.class);

    }

    @Override
    public URI createRepair(String prod_id, String tech_id, UriComponentsBuilder uriComponentsBuilder) {

        Product product;
        Repair repair;

        product = productRepository.findById(prod_id).get();

        repair = new Repair(product);

        productRepository.save(product);
        repairRepository.save(repair);

        //Builds URI path
        UriComponents uriComponents = uriComponentsBuilder.path("/repairs/{id}").buildAndExpand(repair.getId());

        return uriComponents.toUri();

    }

    @Override
    public Repair getRepairByID(String id) {

        if (!repairRepository.findById(id).isPresent()) {
            String msg = "Repair not found with id: " + id;
            throw new NoSuchElementException(msg);
        }

        return repairRepository.findById(id).get();
    }

    @Override
    public void pauseRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);


        if (repair.isPresent()) {
            Repair repairModel = repair.get();

            System.out.println(repairModel.getState());
            if (repairModel.getState().equals(RepairState.ON_GOING)) {

                repairModel.setState(RepairState.PAUSED);
                repairModel.setPausedAt(new Date());

                repairRepository.save(repairModel);
                return;
            }
            throw new IllegalRepairOperationException("Repair must be on-going before a pause");

        }

        throw new NoSuchElementException("Repair not found with id: " + id);
    }

    @Override
    public void resumeRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();

            if (repairModel.getState().equals(RepairState.PAUSED)) {

                repairModel.setState(RepairState.ON_GOING);

                repairRepository.save(repairModel);
                return;
            }

            throw new IllegalRepairOperationException("Repair must be paused before it can be resumed");
        }

        throw new NoSuchElementException("Repair not found :(");
    }

    @Override
    public void cancelRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();
            Product productModel = repairModel.getProduct();

            if (!repairModel.getState().equals(RepairState.FINISHED)) {

                // Set all spareparts states to available
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
            throw new IllegalRepairOperationException("Repair can not be cancelled when finished, dumb ass");
        }
        throw new NoSuchElementException("Repair not found");
    }

    @Override
    public void finishRepair(String id) {

        Optional<Repair> repair = repairRepository.findById(id);

        if (repair.isPresent()) {

            Repair repairModel = repair.get();
            Product productModel = repairModel.getProduct();

            if (repairModel.getState().equals(RepairState.ON_GOING)) {

                repairModel.setState(RepairState.FINISHED);
                repairModel.setEndDate(new Date());

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
        throw new NoSuchElementException("Repair not found :(");
    }

    @Override
    public void addSparePart(String id, String sparepart_id) {

        Optional<Repair> repair = repairRepository.findById(id);
        Optional<SparePart> sparePart = sparePartRepository.findById(sparepart_id);

        if (repair.isPresent() && sparePart.isPresent()) {

            Repair repairModel = repair.get();
            SparePart sparePartModel = sparePart.get();

            // Check if the repair product and given sparepart is compatible
            // TODO: Remove comparability check - Let the user handle that
            //if (sparePartModel.getModel().equals(repairModel.getProduct().getModel())) {
            if (true) {
                // Check if the repair already contains this sparepart
                // TODO: The check fails in spotting duplicates
                if (!repairModel.getAddedSpareParts().contains(sparePartModel)) {
                    sparePartModel.setState(SparePartState.RESERVED);
                    repairModel.addSparePart(sparePartModel);

                    repairRepository.save(repairModel);
                    sparePartRepository.save(sparePartModel);

                } else {
                    throw new IllegalRepairOperationException("Repair can not contain the same spare-part twice");
                }
            } else {

                throw new IncompatibleSparepartTypeException("Bonjour, spareparts and repair product is incompatible");
            }

            return;

        }

        throw new NoSuchElementException("Could not add the sparepart to repair");


    }

    @Override
    public void removeSparePart(String repairId, String sparepartId) {

        Optional<Repair> repair = repairRepository.findById(repairId);
        Optional<SparePart> sparePart = sparePartRepository.findById(sparepartId);

        if (repair.isPresent() && sparePart.isPresent()) {

            Repair repairModel = repair.get();
            SparePart sparePartModel = sparePart.get();

            sparePartModel.setState(SparePartState.AVAILABLE);
            repairModel.removeSparePart(sparePartModel);

            repairRepository.save(repairModel);
            sparePartRepository.save(sparePartModel);

            return;
        }

        throw new NoSuchElementException("The current repair does not contain the requested sparepart");
    }
}
