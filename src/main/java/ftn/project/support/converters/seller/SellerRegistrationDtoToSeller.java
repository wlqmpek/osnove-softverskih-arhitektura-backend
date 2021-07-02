package ftn.project.support.converters.seller;

import ftn.project.models.Authority;
import ftn.project.models.Seller;
import ftn.project.services.AuthorityService;
import ftn.project.web.dto.seller.SellerRegisterationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class SellerRegistrationDtoToSeller implements Converter<SellerRegisterationDto, Seller> {

    @Autowired
    private AuthorityService authorityService;

    private Long authority = 2L;

    @Override
    public Seller convert(SellerRegisterationDto sellerRegisterationDto) {

        Seller seller = new Seller();
        if(sellerRegisterationDto.getPassword().equals(sellerRegisterationDto.getRepeatedPassword())) {
            seller.setFirstName(sellerRegisterationDto.getFirstName());
            seller.setLastName(sellerRegisterationDto.getLastName());
            seller.setUsername(sellerRegisterationDto.getUsername());
            seller.setPassword(sellerRegisterationDto.getPassword());
            seller.setEmail(sellerRegisterationDto.getEmail());
            seller.setAddress(sellerRegisterationDto.getAddress());
            seller.setName(sellerRegisterationDto.getName());
            seller.setRegistrationDate(LocalDate.now());
            seller.setBlocked(false);
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authorityService.findOne(authority));
            seller.setAuthorities(authorities);

        } else {
            throw new RuntimeException("Passwords do not match!");
        }

        return seller;
    }
}
