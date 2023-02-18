package ftn.project.web.dto.article;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
public class ArticleToBeIndexedDto {

    private Long articleId;
    private String name;
    private Double price;

    private String pdfName;

    private String sellerUsername;

}
