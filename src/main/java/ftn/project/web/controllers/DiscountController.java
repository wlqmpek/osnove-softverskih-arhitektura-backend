package ftn.project.web.controllers;

import ftn.project.models.Discount;
import ftn.project.services.ArticleService;
import ftn.project.services.DiscountService;
import ftn.project.services.SellerService;
import ftn.project.support.InvalidArticleException;
import ftn.project.support.converters.discount.DiscountFromFrontDtoToDiscount;
import ftn.project.support.converters.discount.DiscountToDiscountToFront;
import ftn.project.web.dto.discount.DiscountFromFrontDto;
import ftn.project.web.dto.discount.DiscountToFrontDto;
import ftn.project.web.dto.order.OrderToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private DiscountToDiscountToFront discountToDiscountToFront;

    @Autowired
    private DiscountFromFrontDtoToDiscount discountFromFrontDtoToDiscount;

    //READ_ALL
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<DiscountToFrontDto>> findAll() {
        System.out.println("Discount findAll hit!");
        List<Discount> discounts = discountService.findAll();
        List<DiscountToFrontDto> discountToFrontDtos = new ArrayList<>();
        for(Discount discount:discounts) {
            discountToFrontDtos.add(discountToDiscountToFront.convert(discount));
        }
        return new ResponseEntity<>(discountToFrontDtos, HttpStatus.OK);
    }

    //READ_ALL_FROM_SELLER
    @GetMapping("/sellers/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<DiscountToFrontDto>> findAllBySeller(@PathVariable("id") Long id) {
        System.out.println("Find all by seller hitted");
        List<Discount> allDiscounts = discountService.findAll();
        List<Discount> discounts = new ArrayList<>();
        for(Discount discount:allDiscounts) {
            if(discount.getSeller().getUserId().equals(id)) {
                discounts.add(discount);
            }
        }
        List<DiscountToFrontDto> discountsToFrontDtos = new ArrayList<>();
        for(Discount discount:discounts) {
            discountsToFrontDtos.add(discountToDiscountToFront.convert(discount));
        }
        return new ResponseEntity<>(discountsToFrontDtos, HttpStatus.OK);
    }

    //CREATE
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<DiscountToFrontDto> create(@Valid @RequestBody DiscountFromFrontDto discountFromFrontDto, Principal principal) throws InvalidArticleException {
        System.out.println("Discount from front " + discountFromFrontDto);
        ResponseEntity response = null;
        Discount createdDiscount = discountFromFrontDtoToDiscount.convert(discountFromFrontDto);
        createdDiscount.setSeller(sellerService.findSellerByUsername(principal.getName()));
        //This **** should trow exception and @ControllerAdvice should pick it up. Many should-s i know.
        discountService.validateDiscount(createdDiscount);
        createdDiscount = discountService.save(createdDiscount);
        articleService.bindDiscountToArticle(createdDiscount);

        DiscountToFrontDto discountToFrontDto;
        if (createdDiscount == null) {
            response = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        } else {
            discountToFrontDto = discountToDiscountToFront.convert(createdDiscount);
            response = new ResponseEntity(discountToFrontDto, HttpStatus.OK);
        }
        return response;
    }

//    @PostMapping
//    @PreAuthorize("hasAnyRole('SELLER')")
//    public ResponseEntity<DiscountToFrontDto> update(@RequestBody @Valid DiscountFromFrontDto discountFromFrontDto) {
//        ResponseEntity response = null;
//        boolean discountIsValid =
//    }
}
