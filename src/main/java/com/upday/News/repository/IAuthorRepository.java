package com.upday.News.repository;

import com.upday.News.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAuthorRepository extends PagingAndSortingRepository<Author, Long> {

    @Query("select authors from Author authors where upper(authors.name) like upper( :name ) ")
    Optional<List<Author>> findByNameIgnoreCaseContaining(@Param("name") String name);
}
