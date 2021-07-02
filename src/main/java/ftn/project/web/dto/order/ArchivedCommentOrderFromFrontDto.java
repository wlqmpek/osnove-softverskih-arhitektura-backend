package ftn.project.web.dto.order;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ArchivedCommentOrderFromFrontDto {
    @NonNull
    private boolean archivedComment;
}
