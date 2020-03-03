package com.zcode.cloud.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@Builder
@AllArgsConstructor
@Data
public class ResponseResult<T> {

    private String code;
    private String message;
    private T data;

    public static <T> ResponseResult success(T data) {
        ResponseResult result = ResponseResult.builder()
                .code(ResponseCode.SUCCESS)
                .data(data)
                .build();
        return result;
    }

    public static ResponseResult failed(String message) {
        ResponseResult result = ResponseResult.builder()
                .code(ResponseCode.FAILED)
                .message(message)
                .build();
        return result;
    }
}
