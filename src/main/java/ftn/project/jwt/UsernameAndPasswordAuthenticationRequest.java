package ftn.project.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;
}
