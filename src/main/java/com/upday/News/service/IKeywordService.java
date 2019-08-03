package com.upday.News.service;

import com.upday.News.entity.Keyword;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

}
