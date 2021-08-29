package ftn.project.support.converters.buyer;

import ftn.project.models.Authority;
import ftn.project.models.Buyer;
import ftn.project.services.AuthorityService;
import ftn.project.web.dto.buyer.BuyerRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BuyerRegistrationDtoToBuyer implements Converter<BuyerRegistrationDto, Buyer> {

    @Autowired
    private AuthorityService authorityService;

    private Long authority = 3L;

    @Override
    public Buyer convert(BuyerRegistrationDto buyerRegistrationDto) {
        Buyer buyer = new Buyer();
        if(buyerRegistrationDto.getPassword().equals(buyerRegistrationDto.getRepeatedPassword())) {
            buyer.setFirstName(buyerRegistrationDto.getFirstName());
            buyer.setLastName(buyerRegistrationDto.getLastName());
            buyer.setUsername(buyerRegistrationDto.getUsername());
            buyer.setPassword(buyerRegistrationDto.getPassword());
            buyer.setAddress(buyerRegistrationDto.getAddress());
            buyer.setBlocked(false);
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authorityService.findOne(authority));
            buyer.setAuthorities(new HashSet<>(authorities));

        } else {
            throw new RuntimeException("Passwords do not match!");
        }

        return buyer;
    }
}
