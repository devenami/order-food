package com.domain.food.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户类
 *
 * @author zhoutaotao
 * @date 2019/5/15
 */
@Getter
@Setter
@NoArgsConstructor
public class UserVO {

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

    /**
     * 部门
     */
    private String department;

}
