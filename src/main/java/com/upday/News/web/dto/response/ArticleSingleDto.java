package com.upday.News.web.dto.response;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ArticleSingleDto extends ArticleBaseDto {

    private long id;
    private String text;
    private Set<KeywordDto> keywords;
    private Set<AuthorDto> authors;
}
