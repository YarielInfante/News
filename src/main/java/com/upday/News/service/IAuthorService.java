package com.upday.News.service;

import com.upday.News.entity.Author;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    /**
     * Creates an Author
     *
     * @param author an instance of an Author to be created
     * @return id of Author created
     */
    Single<Long> addAuthor(Author author);

    /**
     * Gets an author by the name passed
     *
     * @param name name of author
     * @return Author found
     */
    Single<Optional<List<Author>>> getByName(String name);

    /**
     * Look for an author based on an id passed.
     *
     * @param id of article
     * @return an instance of the article found
     * @see Author
     * @see Single
     */
    Single<Author> getOneId(long id);
}
