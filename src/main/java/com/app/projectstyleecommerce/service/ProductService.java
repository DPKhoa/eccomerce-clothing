package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ProductService extends CommonService<ProductEntity, Long> {
    ProductEntity update(Long id, Map<String, Object> updateProduct, MultipartFile file) throws Exception;
    String uploadImage(Long productId, MultipartFile file) throws IOException;
}
