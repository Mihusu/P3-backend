package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
public class  WriteOffTicketController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    WriteOffTicketServiceImpl writeOffTicketService;

    // private static int writeoffs = ThreadLocalRandom.current().nextInt(0,200); // Todo: find out what this is used for

    @GetMapping("/writeoffs")
    public ResponseEntity<?> getAllWriteOffTickets() {

        return new ResponseEntity<>(writeOffTicketService.getAllWriteOffTickets(), HttpStatus.OK);
    }

    @GetMapping("/writeoffs/{woId}")
    public ResponseEntity<?> getWriteOffTicketById(@PathVariable String woId) {

        try {
            return new ResponseEntity<>(writeOffTicketService.getWriteOffTicketById(woId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/writeoffs/create", consumes = "application/json")
    public ResponseEntity<?> createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, @RequestParam(value = "prod_id") String prod_id, @RequestParam(value = "tech_id") String tech_id)
    {
        try {
            System.out.println(woForm.getReason());
            System.out.println(woForm.getMarkedParts());
            System.out.println(tech_id);
            tech_id = tech_id.replace("%20"," ");
            System.out.println(tech_id);

            // System.out.println("Before creating WOT");
            writeOffTicketService.createWriteOffTicket(woForm, prod_id, tech_id);
            return new ResponseEntity<>("Write-off created", HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/writeoffs/{id}/approve")
    public ResponseEntity<?> approveWriteOffTicket(@PathVariable String id) {
        try {
            writeOffTicketService.approveWriteOffTicket(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Write-off approved", HttpStatus.OK);
    }

    @GetMapping("/writeoffs/{id}/disapprove")
    public ResponseEntity<?> disApproveWriteOffTicket(@PathVariable String id) {
        try {
            writeOffTicketService.disApproveWriteOffTicket(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("I disapprove of this Write-off!", HttpStatus.OK);
    }
}