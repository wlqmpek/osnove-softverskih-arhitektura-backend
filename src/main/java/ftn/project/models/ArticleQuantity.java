package ftn.project.models;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
@Entity
@Table(name = "article_quantity")
public class ArticleQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_quantity_id")
    private Long articleQuantityId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Article article;

    @NonNull
    @Column(nullable = false)
    private int quantity;
}
