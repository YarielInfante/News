package com.upday.News.web.dto.response;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleSingleDto extends ArticleBaseDto {

    private long id;
    private String text;
}
