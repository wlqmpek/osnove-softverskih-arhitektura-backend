package ftn.project.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "time")
    private LocalDateTime time;

    @NotNull
    @Column
    private boolean delivered;

    @Column
    private int rating;

    @Column
    private String comment;

    @Column(name = "anonymus_comment")
    private boolean anonymusComment;

    @Column(name = "archived_comment")
    private boolean archivedComment;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Buyer buyer;

    @ToString.Exclude
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private Set<ArticleQuantity> articleQuantity;
}
