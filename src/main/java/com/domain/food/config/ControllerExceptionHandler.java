package com.domain.food.config;

import com.domain.food.consts.Constant;
import com.domain.food.consts.ErrorCode;
import com.domain.food.domain.Result;
import com.domain.food.utils.HttpUtil;
import com.domain.food.utils.IoUtil;
import com.domain.food.utils.JsonUtil;
import com.domain.food.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异常处理器
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Controller
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private static final Map<Integer, HttpStatusHandler> httpCodeHandlerMap = new ConcurrentHashMap<>();

    private ConfigProperties properties;

    public ControllerExceptionHandler(ConfigProperties properties) {
        this.properties = properties;
        httpCodeHandlerMap.put(404, new HttpStatusHandler404());
        httpCodeHandlerMap.put(500, new HttpStatusHandler500());
    }

    @RequestMapping("/error/{code}")
    public void handleErrorPage(@PathVariable(name = "code") int code, Exception e) throws IOException {
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
        HttpStatusHandler handler = httpCodeHandlerMap.get(code);
        try {
            boolean success = handler.handle(request, response, e);
            if (!success) throw new Exception("状态码处理出错");
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            Result result = Result.success(ErrorCode.BAD);
            response.getWriter().print(JsonUtil.toJson(result));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public void handleUnknowException(Exception e) throws IOException {
        int status = HttpUtil.getHttpServletResponse().getStatus();
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
        HttpStatusHandler handler = httpCodeHandlerMap.get(status);
        try {
            boolean success = handler.handle(request, response, e);
            if (!success) throw new Exception("状态码处理出错");
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            Result result = Result.success(ErrorCode.BAD);
            response.getWriter().print(JsonUtil.toJson(result));
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e) {
        return Result.success(e.getErrorCode());
    }

    /**
     * 异常状态吗处理器
     */
    interface HttpStatusHandler {
        boolean handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Throwable;
    }

    /**
     * 用于处理 404 异常
     */
    class HttpStatusHandler404 implements HttpStatusHandler {

        private String html;

        @Override
        public boolean handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Throwable {
            response.setContentType("text/html; charset=" + Constant.DEFAULT_CHARSET);
            response.getWriter().print(getHtml());
            return true;
        }

        private String getHtml() throws FileNotFoundException {
            // 第一次进行读取错误码
            if (StringUtil.isBlank(html)) {
                String page404 = properties.getWeb().getPage404();
                if (StringUtil.isBlank(page404)) {
                    html = Constant.DEFAULT_TEXT_ERROR;
                } else {
                    html = IoUtil.readString(page404);
                }
            }
            return html;
        }
    }

    /**
     * 用于处理 500 异常
     */
    class HttpStatusHandler500 implements HttpStatusHandler {

        @Override
        public boolean handle(HttpServletRequest request, HttpServletResponse response, Exception e) throws Throwable {
            log.error(e.getMessage(), e);
            response.setContentType("application/json; charset=" + Constant.DEFAULT_CHARSET);
            boolean showStack = properties.getWeb().isShowStack();
            if (showStack) {
                e.printStackTrace(response.getWriter());
            } else {
                Result result = Result.success(ErrorCode.BAD);
                response.getWriter().print(JsonUtil.toJson(result));
            }
            return true;
        }
    }

}
