package com.upday.News.web.mapper;

import com.upday.News.entity.Article;
import com.upday.News.entity.ArticleKeyword;
import com.upday.News.entity.Keyword;
import com.upday.News.utility.DateUtil;
import com.upday.News.web.dto.request.AddArticleRequest;
import com.upday.News.web.dto.response.ArticleResponse;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Size;
import java.util.List;
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
    public static Article toArticle(final AddArticleRequest addArticleRequest) {

        Article articleEntity = new Article();

        BeanUtils.copyProperties(addArticleRequest, articleEntity);

        articleEntity.setPublishDate(DateUtil.stringToDate(addArticleRequest.getPublishDate()));

        // creating ArticleKeyword instances based on the string array of keywords
        List<ArticleKeyword> articleKeywords = addArticleRequest.getKeywords().stream()
                .map(keyword -> new ArticleKeyword(articleEntity, new Keyword(keyword)))
                .collect(Collectors.toList());

        articleEntity.setKeywords(articleKeywords);

        return articleEntity;
    }

    /**
     * Maps an instance of Article entity to an ArticleResponse.
     *
     * @param article instance of Article entity
     * @return an instance of ArticleResponse
     * @see BeanUtils
     */
    public static ArticleResponse toArticleResponse(final Article article) {

        ArticleResponse articleResponse = new ArticleResponse();

        BeanUtils.copyProperties(article, articleResponse);

        articleResponse.setPublishDate(DateUtil.dateToString(article.getPublishDate()));

        List<@Size(min = 1, max = 300) String> keywords = article.getKeywords().stream()
                .map(kw -> kw.getKeyword().getKeyword())
                .collect(Collectors.toList());

        articleResponse.setKeywords(keywords);

        return articleResponse;
    }
}
