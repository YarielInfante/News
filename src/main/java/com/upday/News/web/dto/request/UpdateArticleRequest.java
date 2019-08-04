package com.upday.News.web.dto.request;


import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

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

    @Override
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$", message = "{invalid.date.format}")
    public String getPublishDate() {
        return super.getPublishDate();
    }

}
