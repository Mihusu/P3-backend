package com.skarp.prio.repairs;

import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.spareparts.SparePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class RepairController {

    @Autowired
    RepairService repairService;

    @Autowired
    SparePartService sparePartService;

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SparePartRepository sparePartRepository;

    @Autowired
    MongoOperations operations;

    @GetMapping("/repairs")
    public ResponseEntity<?> getRepairList(@RequestParam(required = false, value = "sortBy") String sortBy, @RequestParam(required = false, value = "LIMIT") String limit)
    {
        return new ResponseEntity<>(repairService.getRepairList(sortBy, limit), HttpStatus.OK);

    }

    @PostMapping("/repairs")
    public ResponseEntity<?> createRepair(@RequestParam(required = true, value = "prod_id") String prod_id,
                                          @RequestParam(required = false, value = "tech_id") String tech_id) {
        try {
            return new ResponseEntity<>(repairService.createRepair(prod_id, tech_id), HttpStatus.CREATED);

        } catch (NoSuchElementException e) { //TODO: Should have an error handler
            String msg = "Failed to create repair: ";

            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/repairs/{id}")
    public ResponseEntity<?> getRepairByID(@PathVariable String id) {

        try {
            return new ResponseEntity<>(repairService.getRepairByID(id), HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/repairs/{id}/pause")
    public ResponseEntity<?> pauseRepair(@PathVariable String id)
    {
        try {
            repairService.pauseRepair(id);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalRepairOperationException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/repairs/{id}/resume")
    public ResponseEntity<?> resumeRepair(@PathVariable String id)
    {
        try {
            repairService.resumeRepair(id);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalRepairOperationException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/repairs/{id}/cancel")
    public ResponseEntity<?> cancelRepair(@PathVariable String id) {
        try {
            repairService.cancelRepair(id);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalRepairOperationException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/repairs/{id}/finish")
    public ResponseEntity<?> finishRepair(@PathVariable String id) {
        try {
            repairService.finishRepair(id);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalRepairOperationException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/repairs/{repairId}/spareparts")
    public ResponseEntity<?> getCompatibleSpareParts(@PathVariable String repairId)
    {
        Optional<Repair> repair = repairRepository.findById(repairId);
        if (repair.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(sparePartService.getRecommendedSpareParts(repair.get()), HttpStatus.OK);
    }

    @PostMapping("/repairs/{repairId}/add/{sparepartId}")
    public ResponseEntity<?> addSparePart(@PathVariable String repairId,
                                          @PathVariable String sparepartId)
    {
        try {
            repairService.addSparePart(repairId, sparepartId);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalRepairOperationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        } catch (IncompatibleSparepartTypeException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/repairs/{repairId}/remove/{sparepartId}")
    public ResponseEntity<?> removeSparePart(@PathVariable String repairId,
                                             @PathVariable String sparepartId)
    {
        try {
            repairService.removeSparePart(repairId, sparepartId);
        } catch (NoSuchElementException e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalRepairOperationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return new  ResponseEntity<>(HttpStatus.OK);

    }

}
