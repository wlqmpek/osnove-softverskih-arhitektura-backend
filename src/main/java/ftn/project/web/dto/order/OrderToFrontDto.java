package ftn.project.web.dto.order;

import ftn.project.support.converters.article.ArticleToArticleToFrontDto;
import ftn.project.web.dto.article_quantity.ArticleQuantityToFrontDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderToFrontDto {
    private Long orderId;
    private LocalDateTime time;
    private boolean delivered;
    private long buyer;
    private int rating;
    private String comment;
    private boolean anonymusComment;
    private boolean archivedComment;
    private ArrayList<ArticleQuantityToFrontDto> articleQuantity;

}
