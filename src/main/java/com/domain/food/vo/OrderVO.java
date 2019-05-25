package com.domain.food.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 订单
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderVO {

    // 主键
    private String orderId;

    // 商品id
    private String productId;

    // 商品名称
    private String productName;

    // 用户编码
    private String userCode;

    // 创建时间
    private Long save;

}
