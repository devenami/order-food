package com.domain.food.config;

import com.domain.food.consts.Constant;
import com.domain.food.consts.ErrorCode;
import com.domain.food.domain.Result;
import com.domain.food.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局的异常处理器，用于处理非业务控制器抛出的异常
 *
 * @author feb13th
 * @since 2019/5/15 23:11
 */
@Controller
@RestControllerAdvice
public class GlobalErrorExceptionHandler implements ErrorController {

    private static final Logger log = LoggerFactory.getLogger(GlobalErrorExceptionHandler.class);

    private ConfigProperties properties;

    public GlobalErrorExceptionHandler(ConfigProperties properties) {
        this.properties = properties;
    }


    /* ------------------------------------------------------------------
     * -------- 处理业务controller抛出的异常
     * ------------------------------------------------------------------
     */
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
            Result result = Result.success(ErrorCode.BAD, null, e.getMessage());
            response.getWriter().print(JsonUtil.toJson(result));
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public Result<Object> handleBusinessException(BusinessException e) {
        return Result.success(e.getErrorCode(), null, e.getExt());
    }




    /* ------------------------------------------------------------------
     * -------- 处理非业务controller抛出的异常
     * ------------------------------------------------------------------
     */

    @GetMapping("/")
    public void index() throws ServletException, IOException {
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    /**
     * 用于返回 HTML 页面
     */
    @RequestMapping(value = "${server.shutdown.path:${shutdown.path:/shutdown}}", produces = MediaType.TEXT_HTML_VALUE)
    public void errorHtml(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=" + Constant.DEFAULT_CHARSET);
        response.setCharacterEncoding(Constant.DEFAULT_CHARSET);
        HttpStatus status = getStatus(request);
        Map<Integer, String> errorPath = properties.getWeb().getErrorPath();
        String path = errorPath.get(status.value());
        if (StringUtil.isBlank(path)) {
            // 处理程序默认行为
            StringBuilder builder = new StringBuilder();
            builder.append("<html><body><h1>页面访问错误</h1>").append(
                    "<p>发生了一个未知异常，请检查访问地址是否正确。</p>")
                    .append("<div id='created'> Time = ").append(DateUtil.formatDateTime(LocalDateTime.now())).append(" </div>")
                    .append("<div id='created'> status = ").append(htmlEscape(status.value())).append(" </div>");
            builder.append("</body></html>");
            response.getWriter().print(builder.toString());
        } else {
            if (path.startsWith("classpath:")) {
                String classloaderRootPath = getClass().getClassLoader().getResource("").getPath();
                String relativePath = path.substring(10);
                relativePath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
                path = classloaderRootPath + relativePath;
                response.getWriter().print(IoUtil.readString(path));
            } else if (path.substring(0, 5).toLowerCase().startsWith("http")) {
                response.sendRedirect(path);
            }
        }
    }

    private String htmlEscape(Object input) {
        return (input != null) ? HtmlUtils.htmlEscape(input.toString()) : null;
    }

    @RequestMapping(value = "${server.shutdown.path:${shutdown.path:/shutdown}}")
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        Map<String, Object> body = new HashMap<>();
        body.put("code", status.value());
        body.put("msg", Constant.DEFAULT_TEXT_UNKNOWN);
        return new ResponseEntity<>(body, status);
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.shutdown.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 该方法在发生异常时，用于转发到错误处理控制器
     */
    @Override
    public String getErrorPath() {
        return null;
    }
}
