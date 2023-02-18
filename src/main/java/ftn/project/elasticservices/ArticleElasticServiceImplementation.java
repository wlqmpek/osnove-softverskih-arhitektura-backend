package ftn.project.elasticservices;

import ftn.project.lucene.indexing.handlers.DocumentHandler;
import ftn.project.lucene.indexing.handlers.PDFHandler;
import ftn.project.models.ArticleElastic;
import ftn.project.web.dto.SimpleQueryEs;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import ftn.project.elasticrepositories.ArticleElasticRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleElasticServiceImplementation implements ArticleElasticService {

    @Value("${pdfs.upload-dir}")
    private String pdfsFilesPath;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    @Autowired
    private ArticleElasticRepository articleElasticRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<ArticleElastic> find(ArticleSearchParams articleSearchParams) {
        QueryBuilder findByName = SearchQueryGenerator
                .createMatchQueryBuilder(new SimpleQueryEs("name", articleSearchParams.getName()));

        QueryBuilder findByDescription = SearchQueryGenerator
                .createMatchQueryBuilder(new SimpleQueryEs("description", articleSearchParams.getDescription()));

        //TODO: Make sure to check the min and max price for null!
        QueryBuilder findByPrice = SearchQueryGenerator
                .createRangeQueryBuilder(new SimpleQueryEs("price", articleSearchParams.getMinPrice() + "-" + articleSearchParams.getMaxPrice()));

        BoolQueryBuilder boolQuery = QueryBuilders
                .boolQuery()
                .must(findByName)
                .must(findByPrice);

        return searchByBoolQuerry(boolQuery)
                .stream()
                .map(sh -> sh.getContent())
                .collect(Collectors.toList());
    }

    private SearchHits<ArticleElastic> searchByBoolQuerry(BoolQueryBuilder boolQuery) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, ArticleElastic.class, IndexCoordinates.of("articles"));
    }



    @Override
    public void index(ArticleElastic articleElastic) {
        articleElasticRepository.save(articleElastic);
    }

    @Override
    public File getResourceFilePath(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            if(url != null) {
                file = new File(url.toURI());
            }
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

    @Override
    public void indexUploadedArticle(ArticleToBeIndexedDto article) throws IOException {
        //todo: handle optional
//        Seller seller = sellerRepository.findSellerByUsername(
//                articleFromFrontDto.getSellerUsername()
//        ).get();

        File pdf = new File(pdfsFilesPath + File.separator + article.getSellerUsername() + File.separator + article.getPdfName());


        String filePath = pdf.getAbsolutePath();
        if(filePath != null){

            // The description is set here
            ArticleElastic articleElasticIndexUnit = getHandler(filePath).getIndexUnit(pdf);
            articleElasticIndexUnit.setArticleId(article.getArticleId());
            articleElasticIndexUnit.setName(article.getName());
            //articleIndexUnit.setDescription(articleFromFrontDto.getDescription());
            articleElasticIndexUnit.setPrice(article.getPrice());
            articleElasticIndexUnit.setSellerUsername(article.getSellerUsername());
            articleElasticIndexUnit.setPdfName(article.getPdfName());
            //articleElasticIndexUnit.setSeller(seller);
            index(articleElasticIndexUnit);
        }

    }
    public DocumentHandler getHandler(String fileName){
        return getDocumentHandler(fileName);
    }


    public static DocumentHandler getDocumentHandler(String fileName) {
        if(fileName.endsWith(".pdf")){
            return new PDFHandler();
        } else {
            return null;
        }
    }


}
