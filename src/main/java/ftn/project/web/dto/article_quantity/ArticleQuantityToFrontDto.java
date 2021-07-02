package ftn.project.web.dto.article_quantity;

import ftn.project.web.dto.article.ArticleToFrontDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ArticleQuantityToFrontDto {
    private Long articleQuantityId;
    private Long orderId;
    private Long articleId;
    private int quantity;
}
