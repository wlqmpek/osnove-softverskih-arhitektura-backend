package ftn.project.web.dto.article;

import ftn.project.models.LogicalOperator;
import lombok.Data;

@Data
public class ArticleSearchParams {

    private String name;
    private LogicalOperator nameDescription;
    private String description;
    private LogicalOperator descriptionPrice;
    private Double minPrice;
    private Double maxPrice;



}
