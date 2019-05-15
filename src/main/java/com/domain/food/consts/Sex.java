package com.domain.food.consts;

import com.domain.food.utils.ObjectUtil;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum Sex {

    MALE(0), FEMALE(1);

    int sex;

    Sex(int sex) {
        this.sex = sex;
    }

    private static final Map<Integer, Sex> map = new ConcurrentHashMap<>();

    static {
        for (Sex value : Sex.values()) {
            map.put(value.sex, value);
        }
    }

    public static boolean exists(Integer sex) {
        if (!ObjectUtil.isNull(sex)) {
            return map.containsKey(sex);
        }
        return false;
    }

    public static Sex get(int sex) {
        return map.get(sex);
    }
}
