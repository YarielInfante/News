package com.upday.News.web.dto.request;

import com.upday.News.web.dto.AuthorBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddAuthorRequest extends AuthorBaseDto {

    @Override
    public String getName() {
        return super.getName();
    }
}
