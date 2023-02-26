package ftn.project.elasticservices;

import ftn.project.models.ArticleElastic;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public interface ArticleElasticService {

    List<ArticleElastic> findByDescription(ArticleSearchParams articleSearchParams);
    List<ArticleElastic> find(ArticleSearchParams articleSearchParams);

    ArticleElastic index(ArticleElastic articleElastic);

    File getResourceFilePath(String path);

    ArticleElastic indexUploadedArticle(ArticleToBeIndexedDto article) throws IOException;

}
