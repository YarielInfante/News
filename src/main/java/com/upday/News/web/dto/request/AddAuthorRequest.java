package com.upday.News.web.dto.request;

import com.upday.News.web.dto.AuthorBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class AddAuthorRequest extends AuthorBaseDto {

    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    @Override
    public String getName() {
        return super.getName();
    }
}
