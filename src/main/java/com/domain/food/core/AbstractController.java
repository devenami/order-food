package com.domain.food.core;

import com.domain.food.config.BusinessException;
import com.domain.food.consts.ErrorCode;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制器抽象类
 *
 * @author feb13th
 * @since 2019/5/16 21:42
 */
public class AbstractController {

    protected Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 参数不能为空
     */
    protected void notBlank(Object obj, String... ext) {
        if (obj instanceof String && StringUtil.isBlank(String.valueOf(obj))) {
            throw new BusinessException(ErrorCode.ILLEGAL_ARGUMENTS, ext);
        }
        if (ObjectUtil.isNull(obj)) {
            throw new BusinessException(ErrorCode.ILLEGAL_ARGUMENTS, ext);
        }
    }

    /**
     * 参数必须是正数，为 >= 0 的数
     */
    protected void greatEqual0(Number number, String... ext) {
        notBlank(number, ext);
        if (number.intValue() < 0) {
            throw new BusinessException(ErrorCode.ILLEGAL_ARGUMENTS, ext);
        }
    }

}
