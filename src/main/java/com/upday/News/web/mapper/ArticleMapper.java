package com.upday.News.web.mapper;

import com.upday.News.entity.*;
import com.upday.News.utility.DateUtil;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.request.AddKeywordRequest;
import com.upday.News.web.dto.request.UpdateArticleRequest;
import com.upday.News.web.dto.response.ArticleDto;
import com.upday.News.web.dto.response.ArticleSingleDto;
import com.upday.News.web.dto.response.AuthorDto;
import com.upday.News.web.dto.response.KeywordDto;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Article's mapper class. It maps the entity Article to Dto and vice versa.
 *
 * @see Article
 * @see AddArticleRequest
 * @see ArticleDto
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
                .map(keywordId -> new ArticleKeyword(articleEntity, new Keyword(keywordId)))
                .collect(Collectors.toSet());

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleAuthor> articleAuthors = addArticleRequest.getAuthors().stream()
                .map(authorId -> new ArticleAuthor(articleEntity, new Author(authorId)))
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
    public static Article updateArticleToArticle(long id, UpdateArticleRequest updateArticleRequest) {

        Article articleEntity = new Article();

        BeanUtils.copyProperties(updateArticleRequest, articleEntity);

        articleEntity.setId(id);

        articleEntity.setPublishDate(updateArticleRequest.getPublishDate() != null ? DateUtil.stringToDate(updateArticleRequest.getPublishDate()) : null);

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleKeyword> articleKeywords = updateArticleRequest.getKeywords() != null ? updateArticleRequest.getKeywords().stream()
                .map(keywordId -> new ArticleKeyword(new ArticleKeywordPK(articleEntity.getId(), keywordId)))
                .collect(Collectors.toSet()) : null;

        // creating ArticleKeyword instances based on the string array of keywords
        Set<ArticleAuthor> articleAuthors = updateArticleRequest.getAuthors() != null ? updateArticleRequest.getAuthors().stream()
                .map(authorId -> new ArticleAuthor(new ArticleAuthorPK(articleEntity.getId(), authorId)))
                .collect(Collectors.toSet()) : null;

        articleEntity.setKeywords(articleKeywords);
        articleEntity.setAuthors(articleAuthors);

        return articleEntity;
    }


    /**
     * Maps an instance of Article entity to an ArticleDto.
     *
     * @param article instance of Article entity
     * @return an instance of ArticleDto
     * @see BeanUtils
     */
    public static Function<Article, ArticleSingleDto> articleToArticleSingleDto = article -> {
        ArticleSingleDto articleResponse = new ArticleSingleDto();

        BeanUtils.copyProperties(article, articleResponse);

        articleResponse.setPublishDate(DateUtil.dateToString(article.getPublishDate()));

        Set<KeywordDto> keywords = article.getKeywords().stream()
                .map(kw -> new KeywordDto(kw.getKeyword().getId(), kw.getKeyword().getKeyword()))
                .collect(Collectors.toSet());

        articleResponse.setKeywords(keywords);

        Set<AuthorDto> authors = article.getAuthors().stream()
                .map(author -> new AuthorDto(author.getAuthor().getId(), author.getAuthor().getName()))
                .collect(Collectors.toSet());

        articleResponse.setAuthors(authors);

        return articleResponse;
    };

    /**
     * Maps an instance of Article entity to an ArticleDto.
     *
     * @param article instance of Article entity
     * @return an instance of ArticleDto
     * @see BeanUtils
     */
    public static Function<Article, ArticleDto> articleToArticleResponse = article -> {
        ArticleDto articleDto = new ArticleDto();

        BeanUtils.copyProperties(article, articleDto);

        articleDto.setPublishDate(DateUtil.dateToString(article.getPublishDate()));

        Set<KeywordDto> keywords = article.getKeywords().stream()
                .map(kw -> new KeywordDto(kw.getKeyword().getId(), kw.getKeyword().getKeyword()))
                .collect(Collectors.toSet());

        articleDto.setKeywords(keywords);

        Set<AuthorDto> authors = article.getAuthors().stream()
                .map(author -> new AuthorDto(author.getAuthor().getId(), author.getAuthor().getName()))
                .collect(Collectors.toSet());

        articleDto.setAuthors(authors);

        return articleDto;
    };
}
