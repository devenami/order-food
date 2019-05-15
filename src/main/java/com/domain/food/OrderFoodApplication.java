package com.domain.food;

import com.domain.food.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OrderFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFoodApplication.class, args);
    }


    @GetMapping("/")
    public String test(User user) {
        return "111";
    }

}
