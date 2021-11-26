package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
public class RepairController {

    @Autowired
    RepairService repairService;

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
                                          @RequestParam(required = false, value = "tech_id") String tech_id,
                                          UriComponentsBuilder uriComponentsBuilder) {
        try {
            return new ResponseEntity<URI>(repairService.createRepair(prod_id, tech_id, uriComponentsBuilder), HttpStatus.CREATED);

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

    @PostMapping("/repairs/pause/{id}")
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

    @PostMapping("/repairs/resume/{id}")
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

    @PostMapping("/repairs/finish/{id}")
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

    @PatchMapping("/repairs/add/{id}/")
    public ResponseEntity<?> addSparePart(@PathVariable String id,
                                          @RequestParam(required = true, value = "sparepart_id") String sparepart_id)
    {
        Repair repair;
        SparePart sparePart;

        try {
            sparePart = sparePartRepository.findById(sparepart_id).get();
            repair = repairRepository.findById(id).get();

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        repair.addSparePart(sparePart);

        repairRepository.save(repair);
        sparePartRepository.save(sparePart);
        productRepository.save(repair.getProduct());

        return ResponseEntity.accepted().build();

    }

}
