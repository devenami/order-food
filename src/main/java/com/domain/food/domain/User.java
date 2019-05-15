package com.domain.food.domain;

import com.domain.food.consts.Sex;
import lombok.*;

/**
 * 用户类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 用户名
     */
    private String username;
    /**
     * 性别
     */
    private int sex;

}
