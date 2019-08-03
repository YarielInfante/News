package com.upday.News.web.rest;

import com.upday.News.service.IAuthorService;
import com.upday.News.web.dto.Response;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Author. Here you can find all author endpoint's operations.
 *
 * @see IAuthorService
 */
@RestController
@RequestMapping("/authors")
public class AuthorRestController {

    private final IAuthorService authorService;

    public AuthorRestController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Gets all author with pagination
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return an instance of Response with data containing a list of authors paginated
     * @see Response
     * @see org.springframework.data.domain.Page
     */
    @GetMapping
    public Single<ResponseEntity<Response>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        return authorService.getAll(pageable)
                .subscribeOn(Schedulers.io())
                .map(authors -> ResponseEntity.ok(Response.successWithData(authors)));

    }
}