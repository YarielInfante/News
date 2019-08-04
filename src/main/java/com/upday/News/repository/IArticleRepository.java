package com.upday.News.repository;

import com.upday.News.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


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

    /**
     * Finds all Articles
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list of articles paginated
     * @see Page
     * @see Pageable
     */
    Page<Article> findAll(Pageable pageable);

    /**
     * Finds all Articles for a given set of authors's id
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @param authors  an array of authors' id
     * @return a list of articles paginated
     * @see Page
     * @see Pageable
     */
    @Query("select articles from Article articles inner join articles.authors authors where authors.author.id in :authorsId")
    Page<Article> findAllByAuthors(Pageable pageable, @Param("authorsId") Long[] authors);

    /**
     * Finds all Articles for a given set of dates
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @param from     date from
     * @param to       date to
     * @return a list of articles paginated
     * @see Page
     * @see Pageable
     */
    Page<Article> findAllByPublishDateBetween(Date from, Date to, Pageable pageable);

    /**
     * Finds all Articles for a given set of keywords
     *
     * @param pageable   pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @param keywordsId an array of keywords' id
     * @return a list of articles paginated
     * @see Page
     * @see Pageable
     */
    @Query("select articles from Article articles inner join articles.keywords keywords where keywords.keyword.id in :keywordsId")
    Page<Article> findAllByKeywords(Pageable pageable, @Param("keywordsId") Long[] keywordsId);
}
