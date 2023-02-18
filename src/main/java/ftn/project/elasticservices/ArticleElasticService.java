package ftn.project.elasticservices;

import ftn.project.models.ArticleElastic;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public interface ArticleElasticService {

    List<ArticleElastic> find(ArticleSearchParams articleSearchParams);

    void index(ArticleElastic articleElastic);

    File getResourceFilePath(String path);

    void indexUploadedArticle(ArticleToBeIndexedDto article) throws IOException;

}
