package com.upday.News.web.dto.request;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Dto class for article creation.
 *
 * @see ArticleBaseDto
 */
@Data
public class AddArticleRequest extends ArticleBaseDto {

    @Override
    @NotNull(message = "{header.required.notnull.message}")
    @NotEmpty(message = "{header.required.empty.message}")
    public String getHeader() {
        return super.getHeader();
    }

    @Override
    @NotNull(message = "{author.required.notnull.message}")
    @NotEmpty(message = "{author.required.empty.message}")
    public String getAuthor() {
        return super.getAuthor();
    }
}
