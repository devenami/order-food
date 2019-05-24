package com.domain.food.consts;

import com.domain.food.config.BusinessException;
import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {

    OK(200, "业务执行成功"),
    BAD(500, "服务器错误"),
    ILLEGAL_ARGUMENTS(1000, "参数错误"),
    CONDITION_CHECK(1001, "业务检查未通过"),
    /*------------------   登陆错误 5000 - 5010   ------------------*/
    LOGIN_USER_NOT_LOGIN(5000, "用户未登录"),
    LOGIN_USER_NOT_EXISTS(5001, "用户不存在"),
    LOGIN_PASSWORD_ERROR(5002, "密码错误"),
    ;

    int code;
    String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 抛出异常
     *
     * @param ext 扩展信息
     * @return
     */
    public void shutdown(String... ext) {
        throw new BusinessException(this, ext);
    }

}
