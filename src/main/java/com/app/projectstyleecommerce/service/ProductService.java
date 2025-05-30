package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.ProductEntity;

import java.util.Map;

public interface ProductService extends CommonService<ProductEntity, Long> {
    ProductEntity update(Long id, Map<String, Object> updateProduct) throws Exception;
}
