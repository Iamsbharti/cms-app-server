package com.cms.deutsche.service;

import com.cms.deutsche.common.ApiResponse;
import com.cms.deutsche.model.Article;
import com.cms.deutsche.model.User;
import com.cms.deutsche.repository.ArticleRepository;
import com.cms.deutsche.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;
    Logger logger = LogManager.getLogger(ArticleService.class);

    public ApiResponse saveArticle(Article article){
        logger.info("Save Article Service::"+ article);

        Article newArticle = new Article();
        // validate contributor
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(article.getContributor()));
        if(!user.isPresent()){
            return new ApiResponse("404", "Invalid Contributor", article);
        }else{
            // save article
            newArticle.setContent(article.getContent());
            newArticle.setStatus("draft");
            newArticle.setContributor(article.getContributor());
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            newArticle.setUpdateTimeStamp(timestamp.toString());
            newArticle = articleRepository.save(newArticle);
            return new ApiResponse("200", "Article Posted", newArticle);
        }
    }

    public ApiResponse getAllApprovedArticles(){
        logger.info("Get All Approved Articles Service::");
        List<Article> articleList = articleRepository.findAllByStatus("approved");
        return new ApiResponse("200", "All Approved Articles", articleList);
    }

    public ApiResponse getAllDraftArticles(){
        logger.info("Get All Draft Articles::");
        List<Article> articleList = articleRepository.findAllByStatus("draft");
        return new ApiResponse("200", "All Draft Articles", articleList);
    }

    public ApiResponse getAllArticles(){
        logger.info("Get All  Articles::");
        List<Article> articleList = articleRepository.findAll();
        return new ApiResponse("200", "All Articles", articleList);
    }

    public ApiResponse updateArticle(Article article){
        logger.info("Update Article::"+article);
        // get article
        Optional<Article> savedArticle = articleRepository.findById(article.getArticleId());
        if(!savedArticle.isPresent()){
            return new ApiResponse("404", "Invalid Article", article);
        }else{
            // save
            Article toUpdateArticle = new Article();
            Article info = savedArticle.get();
            toUpdateArticle.setArticleId(info.getArticleId());
            toUpdateArticle.setStatus(article.getStatus());
            toUpdateArticle.setContent(article.getContent());
            toUpdateArticle.setApprover(article.getApprover());
            toUpdateArticle.setContributor(info.getContributor());
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            toUpdateArticle.setUpdateTimeStamp(timestamp.toString());
            articleRepository.save(toUpdateArticle);
            return new ApiResponse("200", "Article Updated", toUpdateArticle);
        }
    }

    public ApiResponse searchArticles(String searchkeyWord){
        logger.info("Search Articles::"+searchkeyWord);
        //List<Article> articles = articleRepository.searchArticlesByKeyWord(searchkeyWord);
        return new ApiResponse("200", "Article Fetched", "");
    }
}
