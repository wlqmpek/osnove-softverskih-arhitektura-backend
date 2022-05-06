package ftn.project.models;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
@Table(name = "discount")
public class Discount {

    @Id
    @GeneratedValue
    @Column(name = "discount_id")
    private Long discountId;

    @Column(name = "percentage", nullable = false)
    private int percentage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Seller seller;

    @ManyToMany(mappedBy = "discounts", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Article> articles;
}
