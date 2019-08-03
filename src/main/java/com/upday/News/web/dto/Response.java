package com.upday.News.web.dto;

import com.upday.News.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

/**
 * Generic Response for http interface.
 *
 * @param <T> type of Class to use
 * @see ErrorCode
 */
@Getter
@Builder
public class Response<T> {

    private ErrorCode errorCode;
    private T data;

    /**
     * It gives a success response with no data.
     *
     * @return an instance of Response
     */
    public static Response successNoData() {
        return Response.builder()
                .build();
    }

    /**
     * It gives a success response with the data passed.
     *
     * @param data the data passed.
     * @param <T>  Type of class to use.
     * @return an instance of Response with data.
     */
    public static <T> Response<T> successWithData(T data) {
        return Response.<T>builder()
                .data(data)
                .build();
    }

    /**
     * It gives an error response with the ErrorCode passed.
     *
     * @param errorCode error code to use
     * @return an instance of Response with ErrorCode
     */
    public static Response error(ErrorCode errorCode) {
        return Response.builder()
                .errorCode(errorCode)
                .build();
    }

    /**
     * It gives an error response with the ErrorCode and the data passed.
     *
     * @param errorCode error code to use
     * @return an instance of Response with ErrorCode
     */
    public static <T> Response<T> error(ErrorCode errorCode, T data) {
        return Response.<T>builder()
                .errorCode(errorCode)
                .data(data)
                .build();
    }
}
