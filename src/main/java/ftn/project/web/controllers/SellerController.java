package ftn.project.web.controllers;

import ftn.project.models.Seller;
import ftn.project.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Seller>> findAll() {
        List<Seller> sellers = sellerService.findAll();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Seller> findOne(@PathVariable("id") Long id) {
        Seller seller = sellerService.findOne(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Seller> create(@RequestBody Seller seller) {
        ResponseEntity response = null;
        seller = sellerService.save(seller);

        response = (seller == null) ?
                new ResponseEntity(seller, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(seller, HttpStatus.CREATED);

        return response;
    }
}
