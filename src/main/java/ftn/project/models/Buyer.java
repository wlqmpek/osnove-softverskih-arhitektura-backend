package ftn.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
@Entity
public class Buyer extends User {

    @Column(name = "address", nullable = false)
    private String address;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer", fetch = FetchType.EAGER)
    private Set<Order> orders;
}
