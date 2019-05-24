package com.domain.food.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity(name = "user")
public class User {

    /**
     * 用户编码
     */
    @Id
    private String userCode;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;

    /**
     * 部门
     */
    private String department;

    /**
     * 创建时间
     */
    private Long save;
}
