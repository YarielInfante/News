package com.upday.News.web.rest;

import com.upday.News.service.IKeywordService;
import com.upday.News.web.dto.Response;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for Keyword. Here you can find all articles endpoint's operations.
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
    public Single<ResponseEntity<Response>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        return keywordService.getAll(pageable)
                .subscribeOn(Schedulers.io())
                .map(keywords -> ResponseEntity.ok(Response.successWithData(keywords)));

    }
}
