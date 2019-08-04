package com.upday.News.repository;

import com.upday.News.entity.Keyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * Finds keywords entity by its keyword value
     *
     * @param keyword a string keyword value
     * @return a list of Keyword entity
     */
    @Query("select keywords from Keyword keywords where upper(keywords.keyword) like upper( :keyword ) ")
    Optional<List<Keyword>> findByKeywordIgnoreCaseContaining(@Param("keyword") String keyword);
}
