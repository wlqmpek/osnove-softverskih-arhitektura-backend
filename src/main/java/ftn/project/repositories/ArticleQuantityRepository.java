package ftn.project.repositories;

import ftn.project.models.ArticleQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleQuantityRepository extends JpaRepository<ArticleQuantity, Long> {


}
