package com.upday.News.web.mapper;

import com.upday.News.entity.Keyword;
import com.upday.News.web.dto.response.KeywordDto;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

public class KeywordMapper {

    public static final Function<Keyword, KeywordDto> keywordToKeywordDto = keyword -> {

        KeywordDto keywordDto = new KeywordDto();

        BeanUtils.copyProperties(keyword, keywordDto);

        return keywordDto;
    };
}
