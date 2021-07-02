package ftn.project.web.dto.order;

import ftn.project.web.dto.article_quantity.ArticleQuantityFromFrontDto;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
// End of lombok
public class InitialOrderFromFrontDto {

    @NotEmpty
    private ArrayList<ArticleQuantityFromFrontDto> articleQuantity;

}
