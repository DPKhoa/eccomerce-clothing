package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface ImageService extends CommonService<ImageEntity, Long>{
     CompletableFuture<ImageEntity> uploadImageAsync(Long productId, MultipartFile file) throws IOException;
     void delete(Long imageId) throws IOException;
}
