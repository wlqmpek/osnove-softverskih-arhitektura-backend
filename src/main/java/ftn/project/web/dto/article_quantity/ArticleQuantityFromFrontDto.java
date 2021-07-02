package ftn.project.web.dto.article_quantity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticleQuantityFromFrontDto {

    @NotNull(message = "Article cant be null!")
    private Long articleId;

    @Min(value = 0, message = "You cant order negative quantity of articles!")
    private int quantity;

}
