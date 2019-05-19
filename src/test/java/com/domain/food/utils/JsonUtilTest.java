package com.domain.food.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonUtilTest {

    @Test
    public void testToJson() {

        List<JsonUtilDomain> list = new ArrayList<>();

        JsonUtilDomain user = new JsonUtilDomain();
        user.setUserCode("1---");
        user.setSex(1);
        user.setTimestamp(System.currentTimeMillis());
        user.setBigDecimal(BigDecimal.TEN);
        user.setDate(new Date());
        user.setLocalDate(LocalDate.now());
        user.setLocalDateTime(LocalDateTime.now());
        System.out.println(JsonUtil.toJson(user));


        list.add(user);
        JsonUtilDomain user2 = new JsonUtilDomain();
        user2.setUserCode("1---");
        user2.setSex(1);
        user2.setTimestamp(System.currentTimeMillis());
        user.setBigDecimal(BigDecimal.TEN);
        user2.setDate(new Date());
        user2.setLocalDate(LocalDate.now());
        user2.setLocalDateTime(LocalDateTime.now());
        list.add(user2);
        System.out.println(JsonUtil.toJson(list));

    }


    @Test
    public void testFromJson() {
        final String objStr = "{\"userCode\":\"1---\",\"sex\":1,\"timestamp\":\"1558253784503\",\"bigDecimal\":\"10\",\"date\":\"1558253784509\",\"localDate\":\"1558195200000\",\"localDateTime\":\"1558253784535\"}";

        JsonUtilDomain user = JsonUtil.fromJson(objStr, JsonUtilDomain.class);
        System.out.println(user.getUserCode());

    }


    @Test
    public void testFromJsonList() {
        final String listStr = "[{\"userCode\":\"1---\",\"sex\":1,\"timestamp\":\"1558253784503\",\"bigDecimal\":\"10\",\"date\":\"1558253784509\",\"localDate\":\"1558195200000\",\"localDateTime\":\"1558253784535\"},{\"userCode\":\"1---\",\"sex\":1,\"timestamp\":\"1558253785209\",\"date\":\"1558253785209\",\"localDate\":\"1558195200000\",\"localDateTime\":\"1558253785209\"}]";
        List<JsonUtilDomain> list = JsonUtil.fromJsonArray(listStr, new TypeReference<List<JsonUtilDomain>>() {
        });
        for (JsonUtilDomain user : list) {
            System.out.println(user.getUserCode());
        }
    }

}