package ftn.project.support.converters.discount;
import ftn.project.models.Article;
import ftn.project.models.Discount;
import ftn.project.support.converters.article.ArticleToArticleToFrontDto;
import ftn.project.support.converters.seller.SellerToSellerToFrontDto;
import ftn.project.web.dto.article.ArticleToFrontDto;
import ftn.project.web.dto.discount.DiscountToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class DiscountToDiscountToFront implements Converter<Discount, DiscountToFrontDto> {

    @Autowired
    private SellerToSellerToFrontDto sellerToSellerToFrontDto;

    @Autowired
    private ArticleToArticleToFrontDto articleToArticleToFrontDto;

    @Override
    public DiscountToFrontDto convert(Discount discount) {
        DiscountToFrontDto discountToFrontDto = new DiscountToFrontDto();
        discountToFrontDto.setDiscountId(discount.getDiscountId());
        discountToFrontDto.setPercentage(discount.getPercentage());
        discountToFrontDto.setStartDate(discount.getStartDate());
        discountToFrontDto.setEndDate(discount.getEndDate());
        discountToFrontDto.setText(discount.getText());
        discountToFrontDto.setSeller(sellerToSellerToFrontDto.convert(discount.getSeller()));
        Set<ArticleToFrontDto> articles = new HashSet<>();
        for(Article article:discount.getArticles()) {
            articles.add(articleToArticleToFrontDto.convert(article));
        }
        discountToFrontDto.setArticles(articles);
        return discountToFrontDto;
    }
}
