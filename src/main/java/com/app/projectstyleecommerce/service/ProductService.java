package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.model.dto.ProductUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ProductService extends CommonService<ProductEntity, Long> {
    CompletableFuture<ProductEntity> update(Long id, ProductUpdateDto updateProduct, MultipartFile file) throws Exception;

}
