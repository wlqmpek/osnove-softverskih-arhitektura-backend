package ftn.project.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
@Document(indexName = "articles")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class ArticleElastic {

    @Id
    private Long articleId;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String sellerUsername;

    @Field(type = FieldType.Keyword)
    private String pdfName;
    //@Column(name = "image_path", nullable = false)

//    @Column(name = "file_name", nullable = false)
//    private String fileName;

}
