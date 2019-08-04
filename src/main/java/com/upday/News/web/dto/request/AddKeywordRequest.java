package com.upday.News.web.dto.request;

import com.upday.News.web.dto.KeywordBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Dto class for adding dto keyword in AddArticleRequest
 *
 * @see AddArticleRequest
 */
@NoArgsConstructor
@Setter
@Getter
public class AddKeywordRequest extends KeywordBaseDto {

    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    @Override
    public String getKeyword() {
        return super.getKeyword();
    }
}
