package com.upday.News.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents ab error object response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorFieldResponse {

    private String field;
    private String message;

}
