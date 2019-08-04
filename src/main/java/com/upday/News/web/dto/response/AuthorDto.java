package com.upday.News.web.dto.response;

import com.upday.News.web.dto.AuthorBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AuthorDto extends AuthorBaseDto {

    private long id;

    public AuthorDto(long id, String name) {
        super(name);
        this.id = id;
    }
}
