package com.domain.food.frontend.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.frontend.service.IProductService;
import com.domain.food.vo.ProductVO;
import com.domain.food.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品控制器
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController {

    @Autowired
    private IProductService productService;


    @ApiOperation("上传商品图片")
    @PostMapping("/image/upload")
    public Result<String> uploadImage(MultipartFile file) {
        return Result.success(productService.uploadImage(file));
    }

    @PostMapping("/add")
    @ApiOperation("新增商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名", required = true),
            @ApiImplicitParam(name = "image", value = "商品图片"),
            @ApiImplicitParam(name = "price", value = "商品价格", required = true)
    })
    public Result<ProductVO> addProduct(UserVO userVO, String name, String image, Float price) {
        notBlank(name, "商品名不能为空");
        greatEqual0(price, "商品价格不能为负数");

        return Result.success(productService.addProduct(userVO, name, image, price));
    }

    @GetMapping("/delete")
    @ApiOperation("删除商品")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    public Result deleteProduct(String productId) {
        notBlank(productId, "商品id不能为空");
        productService.deleteProduct(productId);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询商品列表")
    public Result<List<ProductVO>> getProductList() {
        return Result.success(productService.getProductList());
    }
}
