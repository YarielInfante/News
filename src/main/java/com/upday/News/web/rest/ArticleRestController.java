package com.upday.News.web.rest;

import com.upday.News.entity.Article;
import com.upday.News.service.IArticleService;
import com.upday.News.utility.DateUtil;
import com.upday.News.web.dto.Response;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.request.UpdateArticleRequest;
import com.upday.News.web.dto.response.ArticleDto;
import com.upday.News.web.dto.response.ArticleSingleDto;
import com.upday.News.web.mapper.ArticleMapper;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Single<ResponseEntity<Response>> addArticle(@Valid @RequestBody final AddArticleRequest addArticleRequest) {

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
    public Single<ResponseEntity<Response<ArticleSingleDto>>> getArticleById(@PathVariable(value = "articleId") long articleId) {
        return articleService.getOneId(articleId)
                .subscribeOn(Schedulers.io())
                .map(article -> ResponseEntity.ok(
                        Response.successWithData
                                (       // Mapping to ArticleDto
                                        Optional.of(article).map(ArticleMapper.articleToArticleSingleDto).get())
                        )
                );
    }

    /**
     * Http Put method to update an Article.
     *
     * @param updateArticleRequest an instance of AddArticleRequest
     * @return a http 201 created status with its uri of entity created. It an error occurs
     */
    @PatchMapping(
            value = "/{articleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response>> updateArticle(@PathVariable long articleId, @Valid @RequestBody final UpdateArticleRequest updateArticleRequest) {


        return articleService.update(ArticleMapper.updateArticleToArticle(articleId, updateArticleRequest))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(Response.successNoData()));
    }


    /**
     * Http Delete method to update an Article.
     *
     * @param articleId id of article
     * @return a http 200 ok status.
     */
    @DeleteMapping(
            value = "/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response>> deleteBook(@PathVariable long articleId) {

        return articleService.delete(articleId)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(Response.successNoData()));

    }

    /**
     * Http Get method to get all articles for a given param.
     *
     * @param pageable  pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @param authorsId an array of authors' id
     * @return a list paginated of articles
     * @see Single
     * @see Response
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Response>> getAll(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "authorsId", required = false) Long[] authorsId,
            @RequestParam(name = "dateFrom", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateFrom,
            @RequestParam(name = "dateTo", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date dateTo
    ) {
        // Validates if an array of authors was passed in order to return a list of articles for a given set of authors id
        if (authorsId != null && authorsId.length > 0) {
            return getAllByAuthorsId(pageable, authorsId);

            // Validates if parameters dateFrom or dateTo were password in order to return a list of articles for a given set of dates
        } else if (dateFrom != null || dateTo != null) {
            dateFrom = dateFrom != null ? dateFrom : DateUtil.minDate();
            dateTo = dateTo != null ? dateTo : DateUtil.maxDate();
            return getAllByPublishDate(pageable, dateFrom, dateTo);
        }

        return getAll(pageable);
    }

    /**
     * Gets all articles
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list paginated of articles
     */
    private Single<ResponseEntity<Response>> getAll(Pageable pageable) {
        return articleService.getAll(pageable)
                .subscribeOn(Schedulers.io())
                .map(articles -> ResponseEntity.ok(mapArticlePage(articles)));
    }


    /**
     * Gets all articles for a given set of authors.
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list paginated of articles
     */
    private Single<ResponseEntity<Response>> getAllByAuthorsId(Pageable pageable, Long[] authorsId) {
        return articleService.getAllByAuthorsId(pageable, authorsId)
                .subscribeOn(Schedulers.io())
                .map(articles -> ResponseEntity.ok(mapArticlePage(articles)));
    }

    /**
     * Gets all articles for a given set of authors.
     *
     * @param pageable pagination parameters. Ex: size = 10, page = 1 , sort = ASC
     * @return a list paginated of articles
     */
    private Single<ResponseEntity<Response>> getAllByPublishDate(Pageable pageable, Date from, Date to) {
        return articleService.getAllByPublishDate(pageable, from, to)
                .subscribeOn(Schedulers.io())
                .map(articles -> ResponseEntity.ok(mapArticlePage(articles)));
    }

    /**
     * Maps articles entities to ArticleDto
     *
     * @param articles a list of articles paginated
     * @return a list of ArticleDto paginated
     */
    private Response<Optional<Page<ArticleDto>>> mapArticlePage(Optional<Page<Article>> articles) {
        return
                Response.successWithData
                        (
                                articles.map(articles1 -> {
                                    List<ArticleDto> list = articles1.getContent()
                                            .stream()
                                            .map(auth -> Optional.of(auth).map(ArticleMapper.articleToArticleResponse).get())
                                            .collect(Collectors.toList());
                                    return new PageImpl<>(list, articles1.getPageable(), articles1.getTotalElements());
                                })
                        )
                ;

    }

}
