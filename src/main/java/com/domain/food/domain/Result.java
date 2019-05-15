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
    private String msg;
    private T data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result<Object> success() {
        return success(null);
    }

    public static <T> Result<T> success(ErrorCode errorCode) {
        return success(errorCode, null);
    }

    public static <T> Result<T> success(T data) {
        return success(ErrorCode.OK, data);
    }

    public static <T> Result<T> success(ErrorCode errorCode, T data) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), data);
    }

}
