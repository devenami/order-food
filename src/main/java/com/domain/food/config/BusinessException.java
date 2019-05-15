package com.domain.food.config;

import com.domain.food.consts.ErrorCode;
import lombok.Getter;

/**
 * 业务逻辑错误
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode() + ":" + errorCode.getMsg());
        this.errorCode = errorCode;
    }

}
