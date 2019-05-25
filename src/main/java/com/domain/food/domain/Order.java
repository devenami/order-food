package com.domain.food.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 订单
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "order")
public class Order {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 创建时间
     */
    private Long save;

}
