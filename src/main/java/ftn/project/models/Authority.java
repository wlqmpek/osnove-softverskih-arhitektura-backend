package ftn.project.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
@Getter
@Setter

@Entity
@ToString
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
