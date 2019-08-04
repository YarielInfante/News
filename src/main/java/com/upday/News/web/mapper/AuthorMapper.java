package com.upday.News.web.mapper;

import com.upday.News.entity.Author;
import com.upday.News.web.dto.request.AddAuthorRequest;
import com.upday.News.web.dto.response.AuthorDto;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * Author's mapper class. It maps the entity Author to Dto and vice versa.
 *
 * @see Author
 */
public class AuthorMapper {

    /**
     * Maps an Author entity to an AuthorDto
     */
    public static final Function<Author, AuthorDto> authorToAuthorDto = author -> {
        AuthorDto authorDto = new AuthorDto();

        BeanUtils.copyProperties(author, authorDto);

        return authorDto;
    };


    /**
     * Maps an AddAuthorRequest to an Author entity
     */
    public static final Function<AddAuthorRequest, Author> addAuthorRequestToAuthor = addAuthorRequest -> {
        Author author = new Author();

        BeanUtils.copyProperties(addAuthorRequest, author);

        return author;
    };
}
