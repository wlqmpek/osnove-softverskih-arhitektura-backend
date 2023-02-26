package ftn.project.web.dto.order;

import ftn.project.models.LogicalOperator;
import lombok.Data;

@Data
public class OrderSearchParams {

    private Double minTotalPrice;
    private Double maxTotalPrice;
    private LogicalOperator priceComment;
    private String comment;

}
