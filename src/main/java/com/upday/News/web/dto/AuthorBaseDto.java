package com.upday.News.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO base Author class.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuthorBaseDto {


    private String name;
}

