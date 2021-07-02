package ftn.project.web.dto.seller;

import ftn.project.models.Authority;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SellerToFrontDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String address;
    private String name;
    private Set<Authority> authorities;
    private boolean isBlocked;
    private double rating;
}
