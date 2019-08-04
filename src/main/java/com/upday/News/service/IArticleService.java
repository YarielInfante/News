package com.upday.News.service;

import com.upday.News.entity.Article;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * This class takes care of all business logic operations on the entity Article. It handles CRUD operations as well as some business rules.
 */
public interface IArticleService {

    /**
     * Saves an article in the database
     *
     * @param article an instance of an Article
     * @return id of Article saved
     * @see Article
     * @see Single
     */
    Single<Long> add(Article article);

    /**
     * Look for an article based on an id passed.
     *
     * @param id of article
     * @return an instance of the article found
     * @see Article
     * @see Single
     */
    Single<Article> getOneId(long id);

    /**
     * Look for of all articles
     *
     * @return a list of articles
     * @see Article
     * @see Article
     * @see List
     */
    Single<Optional<Page<Article>>> getAll(Pageable pageable);

    /**
     * Update an article based of the instance of article passed. To find which article to update it takes the Id of the instance passed.
     *
     * @param article an instance of the article to be updated with the changes wanted.
     * @return calls onComplete when updated successfully
     * @see Article
     * @see Completable
     */
    Completable update(Article article);

    /**
     * Deletes an article based on the id passed.
     *
     * @param id of article
     * @return call onComplete when deleted successfully
     * @see Completable
     */
    Completable delete(long id);

    /**
     * Look of all articles for given set of authors id
     *
     * @return a list of articles
     * @see Article
     * @see Article
     * @see List
     */
    Single<Optional<Page<Article>>> getAllByAuthorsId(Pageable pageable, Long[] authorsId);

    /**
     * Look of all articles for given date
     *
     * @return a list of articles
     * @see Article
     * @see Article
     * @see List
     */
    Single<Optional<Page<Article>>> getAllByPublishDate(Pageable pageable, Date from, Date to);

    /**
     * Look of all articles for given set of keywords id
     *
     * @return a list of articles
     * @see Article
     * @see Article
     * @see List
     */
    Single<Optional<Page<Article>>> getAllByKeywordsId(Pageable pageable, Long[] keywordsId);
}
