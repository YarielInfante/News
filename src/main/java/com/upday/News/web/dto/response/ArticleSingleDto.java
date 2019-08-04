package com.upday.News.web.dto.response;

import com.upday.News.web.dto.ArticleBaseDto;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.request.AddKeywordRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
public class ArticleSingleDto extends ArticleBaseDto {

    private long id;
    private String text;
    private Set<KeywordDto> keywords;
    private Set<AuthorDto> authors;
}
