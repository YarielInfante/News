package com.upday.News.web.dto;

import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.request.AddKeywordRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO base article class.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBaseDto {

    private String header;
    private String shortDescription;
    private String publishDate;
    private Set<AddKeywordRequest> keywords;
    private Set<AddAuthorRequest> authors;
}
