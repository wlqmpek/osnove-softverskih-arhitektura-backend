package ftn.project.support.converters.seller;

import ftn.project.models.Article;
import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.models.Seller;
import ftn.project.services.SellerService;
import ftn.project.web.dto.seller.SellerToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SellerToSellerToFrontDto implements Converter<Seller, SellerToFrontDto> {

    @Autowired
    private SellerService sellerService;

    @Override
    public SellerToFrontDto convert(Seller seller) {

        SellerToFrontDto sellerToFrontDto = new SellerToFrontDto();
        sellerToFrontDto.setId(seller.getUserId());
        sellerToFrontDto.setFirstName(seller.getFirstName());
        sellerToFrontDto.setLastName(seller.getLastName());
        sellerToFrontDto.setUsername(seller.getUsername());
        sellerToFrontDto.setEmail(seller.getEmail());
        sellerToFrontDto.setAddress(seller.getAddress());
        sellerToFrontDto.setName(seller.getName());
        sellerToFrontDto.setAuthorities(seller.getAuthorities());
        sellerToFrontDto.setBlocked(seller.isBlocked());
        sellerToFrontDto.setRating(sellerService.calculateRating(seller));
        System.out.println("Seller front dto " + sellerToFrontDto);
        return sellerToFrontDto;
    }

}
