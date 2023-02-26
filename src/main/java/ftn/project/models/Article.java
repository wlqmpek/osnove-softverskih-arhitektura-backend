package ftn.project.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
// End of lombok
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "name", nullable = true, columnDefinition = "LONGTEXT")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "pdf_name")
    private String pdfName;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Seller seller;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "article_discount", joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "discount_id")})
    private Set<Discount> discounts = new HashSet<>();

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private Set<ArticleQuantity> articleQuantity = new HashSet<>();
}
