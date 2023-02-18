package ftn.project.elasticrepositories;

import ftn.project.models.ArticleElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleElasticRepository extends ElasticsearchRepository<ArticleElastic, Long> {

    List<ArticleElastic> findAllByDescription(String description);

}
