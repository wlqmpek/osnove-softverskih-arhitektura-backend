package ftn.project.web.dto.order;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class BuyerFeedbackOrderFromFront {

    @NotBlank(message = "Comment is required!")
    @NonNull
    private String comment;

    @Min(value = 1, message = "Minimal rating should be 1!")
    @Max(value = 5, message = "Maximum rating should be 5!")
    private int rating;

    @NonNull
    private boolean anonymusComment;
}
