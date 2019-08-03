package com.upday.News.service;

import com.upday.News.entity.Author;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * This class takes care of all business logic operations on the entity Author. It handles CRUD operations as well as some business rules.
 */
public interface IAuthorService {

    /**
     * Get all a list of all authors paginated
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list of authors paginated
     */

    Single<Optional<Page<Author>>> getAll(Pageable pageable);
}
