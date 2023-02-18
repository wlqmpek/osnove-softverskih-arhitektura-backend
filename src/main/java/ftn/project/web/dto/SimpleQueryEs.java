package ftn.project.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleQueryEs {

    private String field;
    private String value;

}
