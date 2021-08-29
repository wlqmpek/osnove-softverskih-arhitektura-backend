package ftn.project.web.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFromFrontDto {

    @NotBlank(message = "Name not entered!")
    private String name;
    @NotBlank(message = "Description not entered!")
    private String description;
    @DecimalMin(value = "0.0", message = "Price must be >= 0")
    private Double price;

}
