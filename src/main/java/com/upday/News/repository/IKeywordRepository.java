package com.upday.News.repository;

import com.upday.News.entity.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * This interfaces takes care of the Keyword's database operations. It has various methods for CRUD (Create, Read, Update, Delete) operations.
 * If a new query is needed, you only need to define a method following the convention of Spring data.
 *
 * @see CrudRepository
 * @see Repository
 * @see Keyword
 */
@Repository
public interface IKeywordRepository extends PagingAndSortingRepository<Keyword, Long> {

    /**
     * Finds a keyword entity by its keyword value
     *
     * @param keyword a string keyword value
     * @return a Keyword entity
     */
    Optional<Keyword> findByKeyword(String keyword);
}
