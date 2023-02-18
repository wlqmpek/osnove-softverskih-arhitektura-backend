package ftn.project.web.dto.order;

import ftn.project.models.LogicalOperator;
import lombok.Data;

@Data
public class OrderSearchParams {

    private String comment;
    private LogicalOperator commentPrice;
    private Double minPrice;
    private Double maxPrice;


}
