package ftn.project.elasticrepositories;

import ftn.project.models.OrderElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderElasticRepository extends ElasticsearchRepository<OrderElastic, Long> {

    List<OrderElastic> findAllByComment(String comment);

}
