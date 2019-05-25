package com.domain.food.dao.impl;

import com.domain.food.core.AbstractDao;
import com.domain.food.dao.IProductDao;
import com.domain.food.domain.Product;
import com.domain.food.vo.ProductVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品dao
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Repository
public class ProductDao extends AbstractDao<String, Product> implements IProductDao {

}
