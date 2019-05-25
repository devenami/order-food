package com.domain.food.frontend.service.impl;

import com.domain.food.config.ConfigProperties;
import com.domain.food.consts.Constant;
import com.domain.food.consts.ErrorCode;
import com.domain.food.core.AbstractService;
import com.domain.food.dao.IProductDao;
import com.domain.food.domain.Product;
import com.domain.food.frontend.service.IProductService;
import com.domain.food.utils.BeanUtil;
import com.domain.food.utils.ExceptionUtil;
import com.domain.food.utils.IoUtil;
import com.domain.food.utils.KeyUtil;
import com.domain.food.vo.ProductVO;
import com.domain.food.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 商品服务实现
 *
 * @author zhoutaotao
 * @date 2019/5/25
 */
@Service
public class ProductService extends AbstractService implements IProductService {

    @Autowired
    private IProductDao productDao;
    @Autowired
    private ConfigProperties config;

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // 验证文件格式是否支持
        List<String> supportSuffix = Arrays.asList(Constant.SUFFIX_IMAGE_SUPPORT.split(Constant.SPILT_COMMA));
        String suffix = fileName.substring(fileName.indexOf(Constant.SPILT_SPOT));
        if (!supportSuffix.contains(suffix)) {
            ErrorCode.UPLOAD_IMAGE_UNSUPPORTED_SUFFIX.shutdown();
        }

        String path = config.getWeb().getImagePath();
        String imageRelativePath = "image_" + System.currentTimeMillis() + "/" + KeyUtil.uuid().concat(suffix);
        String imagePath = IoUtil.localPath(path, imageRelativePath);

        // 将文件存储到本地
        IoUtil.createFile(imagePath);
        try {
            file.transferTo(new File(imagePath));
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
        return imageRelativePath;
    }

    @Override
    public ProductVO addProduct(UserVO userVO, String name, String image, Float price) {
        Product product = new Product();
        product.setId(KeyUtil.uuid());
        product.setUserCode(userVO.getUserCode());
        product.setName(name);
        product.setImage(image);
        product.setPrice(price);
        product.setSave(System.currentTimeMillis());

        productDao.save(product);
        ProductVO productVO = new ProductVO();
        BeanUtil.copy(product, productVO);
        return productVO;
    }

    @Override
    public void deleteProduct(String productId) {
        productDao.deleteById(productId);
    }

    @Override
    public List<ProductVO> getProductList() {
        List<Product> products = productDao.findAll();
        List<ProductVO> list = new ArrayList<>();
        products.stream()
                .sorted((a, b) -> (int) (b.getSave() - a.getSave()))
                .forEach(product -> {
                    ProductVO productVO = new ProductVO();
                    BeanUtil.copy(product, productVO);
                    productVO.setProductId(product.getId());
                    list.add(productVO);
                });
        return list;
    }
}
