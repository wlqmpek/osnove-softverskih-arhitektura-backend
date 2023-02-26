package ftn.project.repositories;

import ftn.project.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByNameIsContaining(String str);

    Article findByArticleId(Long id);

    List<Article> findByArticleIdIn(List<Long> ids);
}
