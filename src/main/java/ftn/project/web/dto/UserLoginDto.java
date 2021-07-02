package ftn.project.web.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginDto {
    private String username;
    private String password;
}
