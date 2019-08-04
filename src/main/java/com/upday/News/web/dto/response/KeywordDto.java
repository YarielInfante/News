package com.upday.News.web.dto.response;

import com.upday.News.web.dto.KeywordBaseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class KeywordDto extends KeywordBaseDto {

    private long id;

    public KeywordDto(long id, String keyword) {
        super(keyword);
        this.id = id;
    }
}
