package ftn.project.elasticservices;

import ftn.project.lucene.indexing.handlers.DocumentHandler;
import ftn.project.lucene.indexing.handlers.PDFHandler;
import ftn.project.models.ArticleElastic;
import ftn.project.models.LogicalOperator;
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
    public List<ArticleElastic> findByDescription(ArticleSearchParams articleSearchParams) {
        return articleElasticRepository.findAllByDescription(articleSearchParams.getDescription());
    }

//    @Override
//    public List<ArticleElastic> find(ArticleSearchParams articleSearchParams) {
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        if (!articleSearchParams.getName().isBlank()) {
//            QueryBuilder findByName = SearchQueryGenerator.createMatchQueryBuilder(
//                    new SimpleQueryEs("name", articleSearchParams.getName()));
//            boolQuery.must(findByName);
//        }
//
//        if (!articleSearchParams.getDescription().isBlank()) {
//            QueryBuilder findByDescription = SearchQueryGenerator.createMatchQueryBuilder(
//                    new SimpleQueryEs("description", articleSearchParams.getDescription()));
//            if (articleSearchParams.getNameDescription() == LogicalOperator.AND) {
//                boolQuery.must(findByDescription);
//            } else if (articleSearchParams.getNameDescription() == LogicalOperator.OR) {
//                boolQuery.should(findByDescription);
//            }
//        }
//
//        if (articleSearchParams.getMinPrice() != null || articleSearchParams.getMaxPrice() != null) {
//
//            Double minPrice = articleSearchParams.getMinPrice() != null ? articleSearchParams.getMinPrice() : 0.0;
//            Double maxPrice = articleSearchParams.getMaxPrice() != null ? articleSearchParams.getMaxPrice() : Double.MAX_VALUE;
//
//            QueryBuilder findByPrice = SearchQueryGenerator.createRangeQueryBuilder(
//                    new SimpleQueryEs("price", minPrice + "-" + maxPrice));
//
//            if (articleSearchParams.getDescriptionPrice() == LogicalOperator.AND) {
//                boolQuery.must(findByPrice);
//
//            } else if (articleSearchParams.getDescriptionPrice() == LogicalOperator.OR) {
//                boolQuery.should(findByPrice);
//            }
//        }
//
//        return searchByBoolQuery(boolQuery)
//                .stream()
//                .map(sh -> sh.getContent())
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ArticleElastic> find(ArticleSearchParams articleSearchParams) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (!articleSearchParams.getName().isBlank()) {
            QueryBuilder findByName = SearchQueryGenerator.createMatchQueryBuilder(
                    new SimpleQueryEs("name", articleSearchParams.getName()));

            if(articleSearchParams.getNameDescription() == LogicalOperator.AND) {
                boolQuery.must(findByName);
            } else {
                boolQuery.should(findByName);
            }
        }

        if (!articleSearchParams.getDescription().isBlank()) {
            QueryBuilder findByDescription = SearchQueryGenerator.createMatchQueryBuilder(
                    new SimpleQueryEs("description", articleSearchParams.getDescription()));
            if (articleSearchParams.getNameDescription() == LogicalOperator.AND) {
                boolQuery.must(findByDescription);
            } else if (articleSearchParams.getNameDescription() == LogicalOperator.OR) {
                boolQuery.should(findByDescription);
            }
        }

        if (articleSearchParams.getMinPrice() != null || articleSearchParams.getMaxPrice() != null) {
            Double minPrice = articleSearchParams.getMinPrice() != null ? articleSearchParams.getMinPrice() : 0.0;
            Double maxPrice = articleSearchParams.getMaxPrice() != null ? articleSearchParams.getMaxPrice() : Double.MAX_VALUE;
            QueryBuilder findByPrice = SearchQueryGenerator.createRangeQueryBuilder(
                    new SimpleQueryEs("price", minPrice + "-" + maxPrice));
            if (articleSearchParams.getDescriptionPrice() == LogicalOperator.AND) {
                boolQuery.must(findByPrice);
            } else if (articleSearchParams.getDescriptionPrice() == LogicalOperator.OR) {
                boolQuery.should(findByPrice);
            }
        }

        List<ArticleElastic> articles = searchByBoolQuery(boolQuery)
                .stream()
                .map(sh -> sh.getContent())
                .collect(Collectors.toList());

        return articles;
    }

    private SearchHits<ArticleElastic> searchByBoolQuery(BoolQueryBuilder boolQuery) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, ArticleElastic.class, IndexCoordinates.of("articles"));
    }

    @Override
    public ArticleElastic index(ArticleElastic articleElastic) {
        return articleElasticRepository.save(articleElastic);
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
    public ArticleElastic indexUploadedArticle(ArticleToBeIndexedDto article) throws IOException {
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
            return index(articleElasticIndexUnit);
        }

        return null;
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
