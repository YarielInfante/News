package com.upday.News.web.mapper;

import com.upday.News.entity.Author;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.response.AuthorDto;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

public class AuthorMapper {

    public static final Function<Author, AuthorDto> authorToAuthorDto = author -> {
        AuthorDto authorDto = new AuthorDto();

        BeanUtils.copyProperties(author, authorDto);

        return authorDto;
    };


    public static final Function<AddAuthorRequest, Author> addAuthorRequestToAuthor = addAuthorRequest -> {
        Author author = new Author();

        BeanUtils.copyProperties(addAuthorRequest, author);

        return author;
    };
}
