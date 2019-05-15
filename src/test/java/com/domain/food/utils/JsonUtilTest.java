package com.domain.food.utils;

import com.domain.food.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonUtilTest {

    @Test
    public void testToJson() {

        List<User> list = new ArrayList<>();

        User user = new User();
        user.setUsername("1--");
        user.setUserCode("1---");
        user.setSex(1);
        System.out.println(JsonUtil.toJson(user));


        list.add(user);
        User user2 = new User();
        user2.setUsername("1--");
        user2.setUserCode("1---");
        user2.setSex(1);
        list.add(user2);
        System.out.println(JsonUtil.toJson(list));

    }


    @Test
    public void testFromJson() {
        final String objStr = "{\"sex\":1,\"userCode\":\"1---\",\"username\":\"1--\"}";

        User user = JsonUtil.fromJson(objStr, User.class);
        System.out.println(user.getUsername());

    }


    @Test
    public void testFromJsonList() {
        final String listStr = "[{\"sex\":1,\"userCode\":\"1---\",\"username\":\"1--\"},{\"sex\":1,\"userCode\":\"2---\",\"username\":\"1--\"}]";
        List<User> list = JsonUtil.fromJsonArray(listStr, User.class);
        for (User user : list) {
            System.out.println(user.getUserCode());
        }
    }

}