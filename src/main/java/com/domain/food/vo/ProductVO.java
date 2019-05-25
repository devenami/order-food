package com.domain.food.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 商品表
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductVO {

    // 主键
    private String productId;
    // 用户编码
    private String userCode;
    // 商品名
    private String name;
    // 图片
    private String image;
    // 价格
    private Float price;
    // 创建时间
    private Long save;
    
}
