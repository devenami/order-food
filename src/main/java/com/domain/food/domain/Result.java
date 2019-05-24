package com.domain.food.domain;

import com.domain.food.consts.ErrorCode;
import lombok.Getter;

/**
 * 业务逻辑统一输出对象
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Getter
public class Result<T> {

    private int code;
    private T data;
    private String msg;
    private String[] ext;

    private Result(int code, String msg, T data, String... ext) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.ext = ext;
    }


    public static Result<Object> success() {
        return success(ErrorCode.OK);
    }

    public static <T> Result<T> success(ErrorCode errorCode) {
        return success(errorCode, null);
    }

    public static <T> Result<T> success(T data) {
        return success(ErrorCode.OK, data);
    }

    public static <T> Result<T> success(ErrorCode errorCode, T data, String... ext) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), data, ext);
    }

}
