package com.upday.News.web.rest;

import com.upday.News.entity.Author;
import com.upday.News.service.IAuthorService;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.mapper.AuthorMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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
    public Single<ResponseEntity<Response>> getAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "name", required = false) String name) {

        if (name != null && !name.isEmpty()) {
            return authorService.getByName(name)
                    .subscribeOn(Schedulers.io())
                    .map(authorList -> ResponseEntity.ok(
                            Response.successWithData(authorList.map(authors -> authors.stream().map(AuthorMapper.authorToAuthorDto))))
                    );
        }
        return authorService.getAll(pageable)
                .subscribeOn(Schedulers.io())
                .map(authorPage -> ResponseEntity.ok(
                        Response.successWithData(authorPage.map(authors -> authors.map(AuthorMapper.authorToAuthorDto))))
                );

    }

    /**
     * Http Post method to create an Author
     *
     * @param addAuthorRequest an instance of AddAuthorRequest
     * @return an http 201 created status with its uri of entity created. It an error occurs
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response>> addAuthor(@Valid @RequestBody AddAuthorRequest addAuthorRequest) {

        Author author = Optional.ofNullable(addAuthorRequest).map(AuthorMapper.addAuthorRequestToAuthor).get();

        return authorService.addAuthor(author)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                        .created(URI.create("/api/authors/" + s))
                        .body(Response.successNoData()));
    }


}