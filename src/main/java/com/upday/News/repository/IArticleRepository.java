package com.upday.News.repository;

import com.upday.News.entity.Article;
import com.upday.News.entity.ArticleAuthor;
import com.upday.News.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * This interfaces takes care of the Article's database operations. It has various methods for CRUD (Create, Read, Update, Delete) operations.
 * If a new query is needed, you only need to define a method following the convention of Spring data.
 *
 * @see CrudRepository
 * @see Repository
 * @see Article
 */
@Repository
public interface IArticleRepository extends PagingAndSortingRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    @Query("select articles from Article articles inner join articles.authors authors where authors.author.id in :authorsId")
    Page<Article> findAllByAuthors(Pageable pageable, @Param("authorsId") Long[] authors);
}
