package com.upday.News.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO base article class.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ArticleBaseDto {

    private String header;
    private String shortDescription;
    private String publishDate;

}
