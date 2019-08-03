package com.upday.News.repository;

import com.upday.News.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthorRepository extends PagingAndSortingRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
