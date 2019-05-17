package com.domain.food.consts;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {

    OK(200, "业务执行成功"),
    BAD(500, "服务器错误"),
    ILLEGAL_ARGUMENTS(1000, "参数错误"),
    ;

    int code;
    String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
