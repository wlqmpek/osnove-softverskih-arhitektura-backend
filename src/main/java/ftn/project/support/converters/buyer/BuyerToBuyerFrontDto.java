package ftn.project.support.converters.buyer;

import ftn.project.models.Buyer;
import ftn.project.web.dto.buyer.BuyerFrontDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class BuyerToBuyerFrontDto implements Converter<Buyer, BuyerFrontDto> {
    @Override
    public BuyerFrontDto convert(Buyer buyer) {
        BuyerFrontDto buyerFrontDto = new BuyerFrontDto();
        buyerFrontDto.setId(buyer.getUserId());
        buyerFrontDto.setFirstName(buyer.getFirstName());
        buyerFrontDto.setLastName(buyer.getLastName());
        buyerFrontDto.setUsername(buyer.getUsername());
        buyerFrontDto.setAddress(buyer.getAddress());
        buyerFrontDto.setAuthorities(buyer.getAuthorities());
        buyerFrontDto.setBlocked(buyer.isBlocked());
        return buyerFrontDto;
    }
}
