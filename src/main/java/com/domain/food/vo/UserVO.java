package com.domain.food.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    /**
     * 用户订单
     */
    private List<OrderVO> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVO userVO = (UserVO) o;
        return Objects.equals(userCode, userVO.userCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCode);
    }
}
