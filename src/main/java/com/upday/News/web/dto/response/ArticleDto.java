package com.upday.News.web.dto.response;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * Dto class for Article
 */
@Getter
@Setter
public class ArticleDto extends ArticleBaseDto {

    private long id;
}
