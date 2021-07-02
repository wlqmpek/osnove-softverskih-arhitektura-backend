package ftn.project.web.dto.discount;

import ftn.project.models.Article;
import ftn.project.web.dto.article.ArticleToFrontDto;
import ftn.project.web.dto.seller.SellerToFrontDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DiscountToFrontDto {
    private Long discountId;
    private int precentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String text;
    private SellerToFrontDto seller;
    private Set<ArticleToFrontDto> articles;
}
