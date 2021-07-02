package ftn.project.web.dto.article;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class ArticleToFrontDto {

    private Long articleId;
    private String name;
    private String description;
    private Double price;
    private String imagePath;
    private Long seller;
    private boolean onSale;

}