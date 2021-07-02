package ftn.project.web.dto.seller;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SellerRegisterationDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String repeatedPassword;
    private String email;
    private String address;
    private String name;
}
