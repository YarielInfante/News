package com.upday.News.web.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    private String author;
    private String text;
    private List<String> keywords;
}
