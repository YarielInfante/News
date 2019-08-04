package com.upday.News.service;

import com.upday.News.entity.Keyword;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * This class takes care of all business logic operations on the entity Keyword. It handles CRUD operations as well as some business rules.
 */
public interface IKeywordService {

    /**
     * Get all a list of all keywords paginated
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list of keywords paginated
     */
    Single<Optional<Page<Keyword>>> getAll(Pageable pageable);

    /**
     * Creates a keyword
     *
     * @param keyword keyword entity to create
     * @return id of keyword created
     */
    Single<Long> addAKeyword(Keyword keyword);

    /**
     * Finds all Keyword by a given keyword name
     *
     * @param keyword keyword name
     * @return a list of keyword
     */
    Single<Optional<List<Keyword>>> getByKeyword(String keyword);


    /**
     * Look for an Keyword based on an id passed.
     *
     * @param id of Keyword
     * @return an instance of the Keyword found
     * @see Keyword
     * @see Single
     */
    Single<Keyword> getOneId(long id);
}
