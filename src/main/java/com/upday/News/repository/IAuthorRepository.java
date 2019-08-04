package com.upday.News.repository;

import com.upday.News.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interfaces takes care of the Author database operations. It has various methods for CRUD (Create, Read, Update, Delete) operations.
 * If a new query is needed, you only need to define a method following the convention of Spring data.
 *
 * @see CrudRepository
 * @see Repository
 * @see Author
 */
@Repository
public interface IAuthorRepository extends PagingAndSortingRepository<Author, Long> {

    /**
     * Look for authors by the name
     *
     * @param name name of authors
     * @return a list of Authors
     */
    @Query("select authors from Author authors where upper(authors.name) like upper( :name ) ")
    Optional<List<Author>> findByNameIgnoreCaseContaining(@Param("name") String name);
}
