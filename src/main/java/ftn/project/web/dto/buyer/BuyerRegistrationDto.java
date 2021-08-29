package ftn.project.web.dto.buyer;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BuyerRegistrationDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String repeatedPassword;
    private String address;
}
