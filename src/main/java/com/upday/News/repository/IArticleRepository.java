package com.upday.News.repository;

import com.upday.News.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


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
}
