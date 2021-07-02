package ftn.project.web.dto.discount;

import ftn.project.models.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DiscountFromFrontDto {
    @Min(value = 1, message = "Discount precentage should be between 1 and 99")
    @Max(value = 99, message = "Discount precentage should be between 1 and 99")
    private int percentage;

    @FutureOrPresent(message = "startDate should be the present or in the future!")
    private LocalDate startDate;
    @Future(message = "endDate should be in the future!")
    private LocalDate endDate;

    @NotBlank
    @NotEmpty
    private String text;

    @NotEmpty
    private Set<Long> articles;
}
