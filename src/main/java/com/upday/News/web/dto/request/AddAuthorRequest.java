package com.upday.News.web.dto.request;

import com.upday.News.web.dto.AuthorBaseDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddAuthorRequest extends AuthorBaseDto {

    public AddAuthorRequest() {
    }

    public AddAuthorRequest(long id, String name) {
        super(id, name);
    }

    @Override
    @NotNull(message = "{required.notnull.message}")
    @NotEmpty(message = "{required.empty.message}")
    public long getId() {
        return super.getId();
    }
}
