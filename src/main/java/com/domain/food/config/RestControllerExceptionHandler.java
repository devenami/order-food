package com.domain.food.config;

import com.domain.food.consts.Constant;
import com.domain.food.consts.ErrorCode;
import com.domain.food.domain.Result;
import com.domain.food.utils.HttpUtil;
import com.domain.food.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理类
 *
 * @author feb13th
 * @since 2019/5/15 22:14
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

    private ConfigProperties properties;

    public RestControllerExceptionHandler(ConfigProperties properties) {
        this.properties = properties;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public void handleUnknownException(Exception e) throws IOException {
        log.error(e.getMessage(), e);
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        response.setContentType("application/json; charset=" + Constant.DEFAULT_CHARSET);
        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
        boolean showStack = properties.getWeb().isShowStack();
        if (showStack) {
            e.printStackTrace(response.getWriter());
        } else {
            Result result = Result.success(ErrorCode.BAD);
            response.getWriter().print(JsonUtil.toJson(result));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e) {
        return Result.success(e.getErrorCode(), null, e.getExt());
    }

}
