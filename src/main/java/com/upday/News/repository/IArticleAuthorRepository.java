package com.upday.News.repository;

import com.upday.News.entity.ArticleAuthor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interfaces takes care of the Article and Author relationship database operations. It has various methods for CRUD (Create, Read, Update, Delete) operations.
 * If a new query is needed, you only need to define a method following the convention of Spring data.
 *
 * @see CrudRepository
 * @see Repository
 * @see ArticleAuthor
 */
@Repository
public interface IArticleAuthorRepository extends CrudRepository<ArticleAuthor, Long> {
}
