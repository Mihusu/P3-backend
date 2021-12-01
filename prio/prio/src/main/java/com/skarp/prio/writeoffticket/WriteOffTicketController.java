package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.ProductRepository;
import org.apache.maven.plugin.descriptor.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@CrossOrigin("*")
@RestController
public class WriteOffTicketController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    WriteOffTicketServiceImpl writeOffTicketService;

    private static int writeoffs = ThreadLocalRandom.current().nextInt(0,200); // Todo: find out what this is used for

    @GetMapping("/writeoffs")
    public ResponseEntity<?> getAllWriteOffTickets() {

        return new ResponseEntity<>(writeOffTicketService.getAllWriteOffTickets(), HttpStatus.OK);
    }

    @PostMapping(value = "/writeoffs/create", consumes = "application/json")
    public ResponseEntity<?> createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, @RequestParam(value = "prod_id") String prod_id, @RequestParam(value = "tech_id") String tech_id)
    {
        try {
            System.out.println(woForm.getReason());
            System.out.println(woForm.getMarkedParts());

            // System.out.println("Before creating WOT");
            writeOffTicketService.createWriteOffTicket(woForm, prod_id, tech_id);
            return new ResponseEntity<>("Write-off created", HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/writeoffs/{id}/approve")
    public ResponseEntity<?> approveWriteOffTicket(@RequestParam (value = "managerCode") String managerCode,
                                                   @PathVariable String id) {
        try {
           // if (!Objects.equals(request, "approve")) throw new InvalidParameterException("Bad request parameter", null);
            if (!Objects.equals(managerCode, "Bertan")) throw new IncorrectManagerCodeException ();
            writeOffTicketService.approveWriteOffTicket(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Write-off approved", HttpStatus.OK);
    }

    @GetMapping("/writeoffs/{id}/disapprove")
    public ResponseEntity<?> disApproveWriteOffTicket(@RequestParam (value = "managerCode") String managerCode,
                                                      @PathVariable String id) {
        try {
            if (!Objects.equals(managerCode, "Bertan")) throw new IncorrectManagerCodeException ();
            writeOffTicketService.disApproveWriteOffTicket(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("I disapprove of this Write-off!", HttpStatus.OK);
    }
}