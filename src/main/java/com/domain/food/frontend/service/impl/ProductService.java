package com.domain.food.frontend.service.impl;

import com.domain.food.config.ConfigProperties;
import com.domain.food.consts.Constant;
import com.domain.food.consts.ErrorCode;
import com.domain.food.core.AbstractService;
import com.domain.food.dao.IProductDao;
import com.domain.food.domain.Product;
import com.domain.food.frontend.service.IProductService;
import com.domain.food.utils.*;
import com.domain.food.vo.ProductVO;
import com.domain.food.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
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
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        // 验证文件格式是否支持
        List<String> supportSuffix = Arrays.asList(Constant.SUFFIX_IMAGE_SUPPORT.split(Constant.SPILT_COMMA));
        String suffix = fileName.substring(fileName.indexOf(Constant.SPILT_SPOT));
        if (!supportSuffix.contains(suffix)) {
            ErrorCode.UPLOAD_IMAGE_UNSUPPORTED_SUFFIX.shutdown();
        }

        fileName = KeyUtil.uuid().concat(suffix);
        String path = config.getWeb().getImagePath();
        String imageRelativePath = "/" + DateUtil.formatDate(LocalDate.now(), DateUtil.FORMAT_DATA_SIMPLE) + "/" + fileName;
        String imagePath = IoUtil.localPath(path, imageRelativePath);

        // 临时目录
        String userDir = System.getProperty("java.io.tmpdir");
        String srcPath = IoUtil.localPath(userDir, fileName);

        // 将spring上传的临时文件转换为图像文件
        File srcFile = IoUtil.createFile(srcPath);
        file.transferTo(srcFile);

        // 将图片处理成缩略图
        ImageUtil.cutSquareImage(srcPath, imagePath);

        // 删除临时文件
        IoUtil.delete(srcPath);

        return imageRelativePath;
    }

    @Override
    public ProductVO addProduct(UserVO userVO, String name, String image, Float price) {
        Product product = new Product();
        product.setId(KeyUtil.uuid());
        product.setUserCode(userVO.getUserCode());
        product.setName(name);
        if (!image.startsWith("/")) {
            image = "/".concat(image);
        }
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
