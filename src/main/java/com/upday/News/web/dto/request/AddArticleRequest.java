package com.upday.News.web.dto.request;

import com.upday.News.web.dto.ArticleBaseDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Dto class for article creation.
 *
 * @see ArticleBaseDto
 */
public class AddArticleRequest extends ArticleBaseDto {

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public String getHeader() {
        return super.getHeader();
    }

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public Set<AddAuthorRequest> getAuthors() {
        return super.getAuthors();
    }
}
