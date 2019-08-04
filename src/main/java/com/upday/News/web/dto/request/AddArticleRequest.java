package com.upday.News.web.dto.request;

import com.upday.News.web.dto.ArticleBaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

/**
 * Dto class for article creation.
 *
 * @see ArticleBaseDto
 */
@Getter
@Setter
public class AddArticleRequest extends ArticleBaseDto {

    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    private String text;

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

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public String getShortDescription() {
        return super.getShortDescription();
    }

    @Override
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$", message = "{invalid.date.format}")
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public String getPublishDate() {
        return super.getPublishDate();
    }

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public Set<AddKeywordRequest> getKeywords() {
        return super.getKeywords();
    }
}
