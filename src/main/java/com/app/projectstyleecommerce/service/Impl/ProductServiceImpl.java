package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.exception.AppException;
import com.app.projectstyleecommerce.model.ErrorModel;
import com.app.projectstyleecommerce.repository.ImageRepository;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.service.ProductService;
import io.micrometer.common.util.StringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl extends CommonServiceImpl<ProductEntity,Long, ProductRepository> implements ProductService {
    public ProductServiceImpl(ProductRepository repo, ImageRepository imageRepository) {
        super(repo);

        this.imageRepository = imageRepository;
    }
    public static final int PRODUCT_MAX_LENGTH = 50;
    public final ImageRepository imageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public void validateProductWhenCreated(ProductEntity product) throws Exception {
        if(StringUtils.isBlank(product.getProduct_name())){
            throw new Exception("Product name is required");
        }
        var name = product.getProduct_name();
        if(name.length() > PRODUCT_MAX_LENGTH){
            throw new Exception("Product name is too long");
        }

    }
    @Override
    public ProductEntity save(ProductEntity entity) throws Exception {
        this.validateProductWhenCreated(entity);
        return getRepo().save(entity);
    }

    @Override
    public ProductEntity findById(Long productId) {
        return getRepo().findById(productId).orElse(null);
    }

    @Override
    public List<ProductEntity> findAll() {
        return getRepo().findAll();
    }

    @Override
    public void deleteById(Long productId) throws Exception {
        getRepo().deleteById(productId);
    }

    @Override
    public boolean existsById(Long productId) throws Exception {
        var productExists = getRepo().findById(productId);
        if(productExists.isEmpty()){
        throw new Exception("Product is not found");
        }
        return true;
    }

    @Override
    public void deleteByIdIns(List<Long> productIds) {
      getRepo().deleteAllById(productIds);
    }


    @Override
        public ProductEntity update(Long id, Map<String, Object> updateProduct,MultipartFile imageFile) throws Exception {
            ProductEntity product = getRepo().findById(id).orElseThrow(()-> new Exception("Product is not found"));
            updateProduct.forEach((key, value)->{
              switch (key){
                  case "product_name": product.setProduct_name((String) value);
                  break;
                  case "description": product.setDescription((String) value);
                  break;

              }
            });
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageEntity = uploadImage(id, imageFile);
            // Có thể thêm imageEntity vào danh sách images của product nếu cần
        }
            return getRepo().save(product);
        }


    public String uploadImage(Long productId, MultipartFile file) throws IOException {
        // Kiểm tra sản phẩm tồn tại
        ProductEntity product = getRepo().findById(productId)
                .orElseThrow(() -> AppException.of(
                        new ErrorModel(HttpStatus.NOT_FOUND.value(), "Khong tim thay san pham")
                ));

        if (file == null || file.isEmpty()) {
            throw AppException.of(new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Tep hinh anh khong duoc de trong"));
        }

        if (!file.getContentType().startsWith("image/")) {
            throw AppException.of(new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Chi chap nhan file hinh anh"));
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw AppException.of(new ErrorModel(HttpStatus.BAD_REQUEST.value(), "Kich thuoc file qua lon, toi da 5MB"));
        }
        // Lưu hình ảnh vào hệ thống tệp
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        Files.createDirectories(uploadDir);
        String fileName = productId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);
        Files.write(filePath, file.getBytes());
        // Lưu thông tin hình ảnh vào ImageEntity
        ImageEntity imageEntity = ImageEntity.builder()
                .image(fileName) // Giả sử ImageEntity có trường name
                .product(product) // Nếu ImageEntity liên kết với ProductEntity
                .build();
      return imageRepository.save(imageEntity).getImage();
    }
}
