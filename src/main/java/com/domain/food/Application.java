package com.domain.food;

import com.domain.food.config.ConfigProperties;
import com.domain.food.domain.User;
import com.domain.food.utils.HttpUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @GetMapping("/")
    public void test() throws ServletException, IOException {
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        HttpServletResponse response = HttpUtil.getHttpServletResponse();
        request.getRequestDispatcher("/index.html").forward(request, response);
    }
}
