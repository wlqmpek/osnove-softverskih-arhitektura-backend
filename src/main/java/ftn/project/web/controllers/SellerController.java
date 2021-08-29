package ftn.project.web.controllers;

import ftn.project.models.Seller;
import ftn.project.services.SellerService;
import ftn.project.support.LogEvents;
import ftn.project.support.converters.seller.SellerRegistrationDtoToSeller;
import ftn.project.support.converters.seller.SellerToSellerToFrontDto;
import ftn.project.web.dto.seller.SellerRegisterationDto;
import ftn.project.web.dto.seller.SellerToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRegistrationDtoToSeller sellerRegistrationDtoToSeller;

    @Autowired
    private SellerToSellerToFrontDto sellerToSellerToFrontDto;


    @GetMapping
    public ResponseEntity<List<SellerToFrontDto>> findAll() {
        List<Seller> sellers = sellerService.findAll();
        List<SellerToFrontDto> sellerToFrontDtos = new ArrayList<>();
        for(Seller s:sellers) {
            sellerToFrontDtos.add(sellerToSellerToFrontDto.convert(s));
        }
        return new ResponseEntity<>(sellerToFrontDtos, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<SellerToFrontDto> findOne(@PathVariable("id") Long id) {
        Seller seller = sellerService.findOne(id);
        SellerToFrontDto sellerToFrontDto = sellerToSellerToFrontDto.convert(seller);
        return new ResponseEntity<>(sellerToFrontDto, HttpStatus.OK);
    }

    //CREATE
    @PostMapping(value = "/registration",consumes = "application/json")
    public ResponseEntity<SellerToFrontDto> create(@RequestBody SellerRegisterationDto sellerRegisterationDto) {
        System.out.println("Seller registration dto " + sellerRegisterationDto);
        ResponseEntity response = null;
        Seller seller = sellerService.save(sellerRegistrationDtoToSeller.convert(sellerRegisterationDto));
        SellerToFrontDto sellerToFrontDto = sellerToSellerToFrontDto.convert(seller);

        response = (sellerToFrontDto == null) ?
                new ResponseEntity(sellerToFrontDto, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(sellerToFrontDto, HttpStatus.CREATED);

        return response;
    }
}
