package com.domain.food.frontend.controller;

import com.domain.food.core.AbstractController;
import com.domain.food.domain.Result;
import com.domain.food.frontend.service.IOrderService;
import com.domain.food.vo.OrderVO;
import com.domain.food.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单控制器
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderController extends AbstractController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("/add")
    @ApiOperation("新增订单")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    public Result<OrderVO> addOrder(UserVO userVO, String productId) {
        notBlank(productId, "商品id不能为空");
        return Result.success(orderService.addOrder(userVO, productId));
    }


    @PostMapping("/delete")
    @ApiOperation("删除订单")
    @ApiImplicitParam(name = "orderId", value = "订单id", required = true)
    public Result deleteOrder(UserVO userVO, String orderId) {
        notBlank(orderId, "订单id不能为空");
        orderService.deleteOrder(userVO, orderId);
        return Result.success();
    }

    @GetMapping("/list/day")
    @ApiOperation("查询订单列表")
    @ApiImplicitParam(name = "day", value = "日期时间戳, 不传查询所有，0:查询当天, 否则查询指定日期")
    public Result<List<OrderVO>> getOrderList(UserVO userVO, Long day) {
        return Result.success(orderService.getOrderList(userVO, day));
    }
}
