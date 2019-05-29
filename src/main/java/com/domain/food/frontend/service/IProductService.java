package com.domain.food.frontend.service;

import com.domain.food.vo.ProductVO;
import com.domain.food.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 商品服务
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
public interface IProductService {

    /**
     * 添加商品
     *
     * @param userVO 用户信息
     * @param name   商品名称
     * @param image  商品图片
     * @param price  商品价格
     * @return 商品信息
     */
    ProductVO addProduct(UserVO userVO, String name, String image, Float price);

    /**
     * 删除商品
     *
     * @param productId 商品id
     * @return 商品信息
     */
    void deleteProduct(String productId);

    /**
     * 上传图片
     *
     * @param file 图片
     * @return 图片地址
     */
    String uploadImage(MultipartFile file) throws IOException;

    /**
     * 查询商品列表
     *
     * @return 商品列表
     */
    List<ProductVO> getProductList();
}
