package ftn.project.web.controllers;

import ftn.project.models.Buyer;
import ftn.project.services.BuyerService;
import ftn.project.support.converters.buyer.BuyerRegistrationDtoToBuyer;
import ftn.project.support.converters.buyer.BuyerToBuyerFrontDto;
import ftn.project.web.dto.buyer.BuyerFrontDto;
import ftn.project.web.dto.buyer.BuyerRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/buyers")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private BuyerRegistrationDtoToBuyer buyerRegistrationDtoToBuyer;

    @Autowired
    private BuyerToBuyerFrontDto buyerToBuyerFrontDto;

//    @GetMapping
//    public ResponseEntity<List<Buyer>> findAll() {
//        List<Buyer> buyers = buyerService.findAll();
//        return new ResponseEntity<>(buyers, HttpStatus.OK);
//    }
//
    @GetMapping(value = "/{id}")
    public ResponseEntity<Buyer> findOne(@PathVariable("id") Long id) {
        System.out.println("FIND BUYER HITTED");
        Buyer buyer = buyerService.findOne(id);
        return new ResponseEntity<>(buyer, HttpStatus.OK);
    }

    //CREATE
    @PostMapping(value = "/registration",consumes = "application/json")
    public ResponseEntity<BuyerFrontDto> create(@RequestBody BuyerRegistrationDto buyerRegistrationDto) {
        System.out.println("Buyer registration  " + buyerRegistrationDto);
        ResponseEntity response = null;
        Buyer buyer = buyerService.save(buyerRegistrationDtoToBuyer.convert(buyerRegistrationDto));
        BuyerFrontDto buyerFrontDto = buyerToBuyerFrontDto.convert(buyer);
        response = (buyer == null) ?
                new ResponseEntity(buyerFrontDto, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(buyer, HttpStatus.CREATED);

        return response;
    }
}
