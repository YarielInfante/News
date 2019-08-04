package com.upday.News.web.rest;

import com.upday.News.entity.Keyword;
import com.upday.News.service.IKeywordService;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.request.AddKeywordRequest;
import com.upday.News.web.dto.response.KeywordDto;
import com.upday.News.web.mapper.KeywordMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Rest controller for Keyword. Here you can find all Keyword endpoint's operations.
 *
 * @see IKeywordService
 */
@RestController
@RequestMapping("/keywords")
public class KeywordRestController {

    private final IKeywordService keywordService;

    @Autowired
    public KeywordRestController(IKeywordService keywordService) {
        this.keywordService = keywordService;
    }


    /**
     * Gets all keywords with pagination
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return an instance of Response with data containing a list of keywords paginated
     * @see Response
     * @see org.springframework.data.domain.Page
     */
    @GetMapping
    public Single<ResponseEntity<Response>> getAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "keyword", required = false) String keyword) {

        if (keyword != null && !keyword.isEmpty()) {
            return keywordService.getByKeyword(keyword)
                    .subscribeOn(Schedulers.io())
                    .map(keywordList -> ResponseEntity.ok(
                            Response.successWithData(keywordList.map(keywords -> keywords.stream().map(KeywordMapper.keywordToKeywordDto))))
                    );
        }
        return keywordService.getAll(pageable)
                .subscribeOn(Schedulers.io())
                .map(keywordPage -> ResponseEntity.ok(
                        Response.successWithData(keywordPage.map(keywords -> keywords.map(KeywordMapper.keywordToKeywordDto))))
                );

    }

    /**
     * Http Post method to create a Keyword
     *
     * @param addKeywordRequest an instance of AddAuthorRequest
     * @return an http 201 created status with its uri of entity created. It an error occurs
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response>> addKeyword(@Valid @RequestBody AddKeywordRequest addKeywordRequest) {

        Keyword keyword = Optional.ofNullable(addKeywordRequest).map(KeywordMapper.addKeywordRequestToKeyword).get();

        return keywordService.addAKeyword(keyword)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                        .created(URI.create("/api/v1/keywords/" + s))
                        .body(Response.successNoData()));
    }


    /**
     * Http Get method to find a Keyword by its ID
     *
     * @param keywordId id of Author
     * @return an http 200 ok status with the Keyword entity. It not found returns an http 404 not found status.
     */
    @GetMapping(
            value = "/{keywordId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response<KeywordDto>>> getAuthorById(@PathVariable(value = "keywordId") long keywordId) {
        return keywordService.getOneId(keywordId)
                .subscribeOn(Schedulers.io())
                .map(keyword -> ResponseEntity.ok(
                        Response.successWithData
                                (       // Mapping to KeywordDto
                                        Optional.of(keyword).map(KeywordMapper.keywordToKeywordDto).get())
                        )
                );
    }
}
