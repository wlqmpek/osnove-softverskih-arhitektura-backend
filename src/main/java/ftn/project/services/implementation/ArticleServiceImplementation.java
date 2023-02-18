package ftn.project.services.implementation;


import ftn.project.elasticservices.ArticleElasticService;
import ftn.project.lucene.indexing.handlers.DocumentHandler;
import ftn.project.lucene.indexing.handlers.PDFHandler;
import ftn.project.models.Article;
import ftn.project.models.ArticleElastic;
import ftn.project.models.Discount;
import ftn.project.repositories.SellerRepository;
import ftn.project.services.ArticleService;
import ftn.project.support.converters.article.ArticleFromFrontDTOToArticle;
import ftn.project.web.dto.article.ArticleFromFrontDto;
import ftn.project.web.dto.article.ArticleSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ftn.project.repositories.ArticleRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImplementation implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Value("${images.upload-dir}")
    private String imagesUploadDir;

    @Value("${pdfs.upload-dir}")
    private String pdfsUploadDir;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ArticleFromFrontDTOToArticle articleFromFrontDTOToArticle;

    @Autowired
    private ArticleElasticService articleElasticService;


    @Override
    public Article findOne(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) {
                throw new NoSuchElementException("Article with id = " + id + " not found!");
        }
        return article.get();
    }

    @Override
    public List<Article> findAll() {

        List<Article> allArticles = new ArrayList<>();

        articleRepository.findAll()
                .forEach(
                        article -> allArticles.add(article));

        return allArticles;
    }

    public List<Article> findByNameIsContaining(String str) {
        //return articleRepository.findByNameIsContaining(str);
        return null;
    }

    @Override
    public Article save(ArticleFromFrontDto articleFromFrontDto) {

        Article article = articleFromFrontDTOToArticle
                .convert(articleFromFrontDto);

        try {
            article.setImageName(saveImage(articleFromFrontDto.getImage(), articleFromFrontDto.getSellerUsername()));
            article.setPdfName(savePDF(articleFromFrontDto.getPdf(), articleFromFrontDto.getSellerUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
//        remove(article.getArticleId());

//        return save(article);
        return null;
    }

    @Override
    public void remove(Long id) {
        articleRepository.delete(findOne(id));
    }

    @Override
    public void bindDiscountToArticle(Discount discount) {
        for(Article article:discount.getArticles()) {
            article.getDiscounts().add(discount);
            update(article);
        }
    }

    @Override
    public String saveImage(MultipartFile image, String sellerUsername) throws IOException {
        String retVal = null;
        if (!image.isEmpty()) {
            byte[] bytes = image.getBytes();
            Path sellerDir = Paths.get(imagesUploadDir + File.separator + sellerUsername);
            if (!Files.exists(sellerDir)) {
                Files.createDirectories(sellerDir);
            }
            Path imagePath = sellerDir.resolve(StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
            String b = imagePath.toAbsolutePath().toString();
            Files.write(imagePath, bytes);
            retVal = image.getOriginalFilename();
        }
        return retVal;
    }

    @Override
    public String savePDF(MultipartFile pdf, String sellerUsername) throws IOException {
        String retVal = null;
        if (!pdf.isEmpty()) {
            byte[] bytes = pdf.getBytes();
            Path sellerDir = Paths.get(pdfsUploadDir + File.separator + sellerUsername);
            if (!Files.exists(sellerDir)) {
                Files.createDirectories(sellerDir);
            }
            Path imagePath = sellerDir.resolve(StringUtils.cleanPath(Objects.requireNonNull(pdf.getOriginalFilename())));
            String b = imagePath.toAbsolutePath().toString();
            Files.write(imagePath, bytes);
            retVal = pdf.getOriginalFilename();
        }
        return retVal;
    }

    @Override
    public List<Article> find(ArticleSearchParams searchParams) {

        List<ArticleElastic> articleElastics = articleElasticService
                .find(searchParams);

        List<Article> articles = articleRepository
                .findByArticleIdIn(
                        articleElastics
                                .stream()
                                .map(articleElastic -> articleElastic.getArticleId())
                                .collect(Collectors.toList())
                );


        return articles;
    }



//    @Override
//    public File getResourceFilePath(String path) {
//        URL url = this.getClass().getClassLoader().getResource(path);
//        File file = null;
//        try {
//            if(url != null) {
//                file = new File(url.toURI());
//            }
//        } catch (URISyntaxException e) {
//            file = new File(url.getPath());
//        }
//        return file;
//    }

//    @Override
//    public void indexUploadedArticle(ArticleFromFrontDto articleFromFrontDto) throws IOException {
//        //todo: handle optional
//        Seller seller = sellerRepository.findSellerByUsername(
//                articleFromFrontDto.getSellerUsername()
//        ).get();
//
//
//        if (articleFromFrontDto.getPdf().isEmpty()) {
//            return;
//        }
//
//        String fileName = savePDF(articleFromFrontDto.getPdf());
//        if(fileName != null){
//            Article articleIndexUnit = getHandler(fileName).getIndexUnit(new File(fileName));
//            articleIndexUnit.setName(articleFromFrontDto.getName());
//            //articleIndexUnit.setDescription(articleFromFrontDto.getDescription());
//            articleIndexUnit.setPrice(articleFromFrontDto.getPrice());
//            articleIndexUnit.setSeller(seller);
//            save(articleIndexUnit);
//        }
//
//    }

    public DocumentHandler getHandler(String fileName){
        return getDocumentHandler(fileName);
    }
    public static DocumentHandler getDocumentHandler(String fileName) {
        if(fileName.endsWith(".txt")){
            return new PDFHandler();
        } else {
            return null;
        }
    }



}
