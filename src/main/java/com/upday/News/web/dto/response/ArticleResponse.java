package com.upday.News.web.dto.response;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Data;

/**
 * Dto class for Article
 */
@Data
public class ArticleResponse extends ArticleBaseDto {

    private long id;
}
