package com.cms.deutsche.repository;

import com.cms.deutsche.model.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article,Integer> {
    List<Article> findAllByContributor(String contributor);
    List<Article> findAllByApprover(String approver);
    List<Article> findAllByStatus(String status);
    List<Article> findAll();

    @Transactional
    @Modifying
    @Query(value="update Article a set a.content = :content, a.status = :status, a.approver = :approver  where a.articleId= :articleId", nativeQuery = true)
    void updateArticle(@Param("content")String content, @Param("status")String status,@Param("approver")String approver, @Param("articleId")Integer articleId);
/**
 * @Transactional
 *     @Query(value = "select * from Article a where a.content in (:search)")
 *     List<Article> searchArticlesByKeyWord(@Param("search")String search);**/

}
