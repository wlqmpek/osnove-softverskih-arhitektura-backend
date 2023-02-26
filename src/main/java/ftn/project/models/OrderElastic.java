package ftn.project.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
@Document(indexName = "orders")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class OrderElastic {

    @Id
    private Long orderId;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Double)
    private Double totalPrice;

    @Field(type = FieldType.Keyword)
    private String buyerUsername;

}
