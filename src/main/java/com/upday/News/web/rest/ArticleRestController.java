package com.upday.News.web.rest;

import com.upday.News.entity.Article;
import com.upday.News.service.IArticleService;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.response.ArticleResponse;
import com.upday.News.web.mapper.ArticleMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

/**
 * Rest controller for Articles. Here you can find all articles endpoint's operations.
 *
 * @see IArticleService
 */
@RestController
@RequestMapping("/articles")
public class ArticleRestController {

    private final IArticleService articleService;

    /**
     * Injecting dependencies.
     *
     * @param articleService
     */
    @Autowired
    public ArticleRestController(IArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * Http Post method to create an Article.
     *
     * @param addArticleRequest an instance of AddArticleRequest
     * @return a http 201 created status with its uri of entity created. It an error occurs
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity> addAuthor(@Valid @RequestBody final AddArticleRequest addArticleRequest) {

        // Mapping to Article entity
        Article article = Optional.of(addArticleRequest).map(ArticleMapper.toArticle).get();

        return articleService.add(article)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity
                        .created(URI.create("/api/articles/" + s)).build());
    }

    /**
     * Http Get method to find an Article by its ID
     *
     * @param articleId id of Article
     * @return an http 200 ok status with the article entity. It not found returns an http 404 not found status.
     */
    @GetMapping(
            value = "/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response<ArticleResponse>>> getArticleById(@PathVariable(value = "articleId") long articleId) {
        return articleService.getOneId(articleId)
                .subscribeOn(Schedulers.io())
                .map(article -> ResponseEntity.ok(
                        Response.successWithData
                                (       // Mapping to ArticleResponse
                                        Optional.of(article).map(ArticleMapper.toArticleResponse).get())
                        )
                );
    }


}
