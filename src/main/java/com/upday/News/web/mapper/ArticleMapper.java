package com.upday.News.web.mapper;

import com.upday.News.entity.*;
import com.upday.News.utility.DateUtil;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.request.AddKeywordRequest;
import com.upday.News.web.dto.request.UpdateArticleRequest;
import com.upday.News.web.dto.response.ArticleResponse;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Article's mapper class. It maps the entity Article to Dto and vice versa.
 *
 * @see Article
 * @see AddArticleRequest
 * @see ArticleResponse
 */
public class ArticleMapper {

    /**
     * Maps an instance of AddArticleRequest to an Article entity.
     *
     * @param addArticleRequest instance of AddArticleRequest
     * @return an instance of Article entity
     * @see BeanUtils
     * @see DateUtil
     */
    public static Function<AddArticleRequest, Article> toArticle = addArticleRequest -> {
        Article articleEntity = new Article();

        BeanUtils.copyProperties(addArticleRequest, articleEntity);

        articleEntity.setPublishDate(DateUtil.stringToDate(addArticleRequest.getPublishDate()));

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleKeyword> articleKeywords = addArticleRequest.getKeywords().stream()
                .map(keyword -> new ArticleKeyword(articleEntity, new Keyword(keyword.getId())))
                .collect(Collectors.toSet());

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleAuthor> articleAuthors = addArticleRequest.getAuthors().stream()
                .map(author -> new ArticleAuthor(articleEntity, new Author(author.getId())))
                .collect(Collectors.toSet());

        articleEntity.setKeywords(articleKeywords);
        articleEntity.setAuthors(articleAuthors);

        return articleEntity;
    };

    /**
     * Maps an instance of AddArticleRequest to an Article entity.
     *
     * @param updateArticleRequest instance of AddArticleRequest
     * @return an instance of Article entity
     * @see BeanUtils
     * @see DateUtil
     */
    public static Article toArticle(long id, UpdateArticleRequest updateArticleRequest) {

        Article articleEntity = new Article();

        BeanUtils.copyProperties(updateArticleRequest, articleEntity);

        articleEntity.setId(id);

        articleEntity.setPublishDate(updateArticleRequest.getPublishDate() != null ? DateUtil.stringToDate(updateArticleRequest.getPublishDate()) : null);

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleKeyword> articleKeywords = updateArticleRequest.getKeywords() != null ? updateArticleRequest.getKeywords().stream()
                .map(keyword -> new ArticleKeyword(new ArticleKeywordPK(articleEntity.getId(), keyword.getId())))
                .collect(Collectors.toSet()) : null;

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleAuthor> articleAuthors = updateArticleRequest.getAuthors() != null ? updateArticleRequest.getAuthors().stream()
                .map(author -> new ArticleAuthor(new ArticleAuthorPK(articleEntity.getId(), author.getId())))
                .collect(Collectors.toSet()) : null;

        articleEntity.setKeywords(articleKeywords);
        articleEntity.setAuthors(articleAuthors);

        return articleEntity;
    }


    /**
     * Maps an instance of Article entity to an ArticleResponse.
     *
     * @param article instance of Article entity
     * @return an instance of ArticleResponse
     * @see BeanUtils
     */
    public static Function<Article, ArticleResponse> toArticleResponse = article -> {
        ArticleResponse articleResponse = new ArticleResponse();

        BeanUtils.copyProperties(article, articleResponse);

        articleResponse.setPublishDate(DateUtil.dateToString(article.getPublishDate()));

        Set<AddKeywordRequest> keywords = article.getKeywords().stream()
                .map(kw -> new AddKeywordRequest(kw.getKeyword().getId(), kw.getKeyword().getKeyword()))
                .collect(Collectors.toSet());

        articleResponse.setKeywords(keywords);

        Set<AddAuthorRequest> authors = article.getAuthors().stream()
                .map(author -> new AddAuthorRequest(author.getAuthor().getId(), author.getAuthor().getName()))
                .collect(Collectors.toSet());

        articleResponse.setAuthors(authors);

        return articleResponse;
    };
}
