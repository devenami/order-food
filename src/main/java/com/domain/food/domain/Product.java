package com.domain.food.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品表
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "product")
public class Product {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 商品名
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 价格
     */
    private Float price;

    /**
     * 创建时间
     */
    private Long save;
}
