package com.upday.News.web.dto.request;

import com.upday.News.web.dto.KeywordBaseDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Dto class for adding dto keyword in AddArticleRequest
 *
 * @see AddArticleRequest
 */
public class AddKeywordRequest extends KeywordBaseDto {

    public AddKeywordRequest() {
    }

    public AddKeywordRequest(long id, String keyword) {
        super(id, keyword);
    }

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public long getId() {
        return super.getId();
    }
}
