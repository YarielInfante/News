package com.upday.News.web.mapper;

import com.upday.News.entity.Keyword;
import com.upday.News.web.dto.request.AddKeywordRequest;
import com.upday.News.web.dto.response.KeywordDto;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * Keyword's mapper class. It maps the entity Author to Dto and vice versa.
 *
 * @see Keyword
 */
public class KeywordMapper {

    /**
     * Maps an Keyword entity to an KeywordDto
     */
    public static final Function<Keyword, KeywordDto> keywordToKeywordDto = keyword -> {

        KeywordDto keywordDto = new KeywordDto();

        BeanUtils.copyProperties(keyword, keywordDto);

        return keywordDto;
    };

    /**
     * Maps an AddKeywordRequest to an Keyword entity
     */
    public static final Function<AddKeywordRequest, Keyword> addKeywordRequestToKeyword = addKeywordRequest -> {

        Keyword keyword = new Keyword();

        BeanUtils.copyProperties(addKeywordRequest, keyword);

        return keyword;

    };
}
