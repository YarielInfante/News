package com.upday.News.web.dto.request;


import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto class for article creation.
 *
 * @see ArticleBaseDto
 */
@Getter
@Setter
public class UpdateArticleRequest extends ArticleBaseDto {

    private long id;
    private String text;

}
