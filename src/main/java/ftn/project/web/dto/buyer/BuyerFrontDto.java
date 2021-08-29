package ftn.project.web.dto.buyer;

import ftn.project.models.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BuyerFrontDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private Set<Authority> authorities;
    private boolean isBlocked;
}
