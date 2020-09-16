package org.lhq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lhq.dao.ProductDao;
import org.lhq.gp.product.entity.Product;
import org.lhq.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * (Product)表服务实现类
 *
 * @author wangdefa
 * @since 2020-09-16 17:46:17
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product>
    implements ProductService {}
