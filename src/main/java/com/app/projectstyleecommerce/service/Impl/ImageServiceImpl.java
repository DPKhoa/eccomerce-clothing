package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.exception.AppException;
import com.app.projectstyleecommerce.model.ErrorModel;
import com.app.projectstyleecommerce.repository.ImageRepository;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.service.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ImageServiceImpl extends CommonServiceImpl<ImageEntity, Long, ImageRepository> implements ImageService {

    @Value("${upload.path}")
    private String uploadPath;
    private final ProductRepository productRepository;
    public ImageServiceImpl(ImageRepository repo, ProductRepository productRepository) {
        super(repo);
        this.productRepository = productRepository;
    }

    @Override
    public ImageEntity save(ImageEntity entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public ImageEntity findById(Long aLong) throws Exception {
        return null;
    }

    @Override
    public List<ImageEntity> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long aLong) throws Exception {

    }

    @Override
    public boolean existsById(Long aLong) throws Exception {
        return false;
    }

    @Override
    public void deleteByIdIns(List<Long> longs) {

    }

    @Async
    public CompletableFuture<ImageEntity> uploadImageAsync(Long productId, MultipartFile file) throws IOException {
        // Kiểm tra sản phẩm tồn tại
        ProductEntity product = productRepository.findById(productId)
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
        fileName = FilenameUtils.getName(fileName); // Loại bỏ các đường dẫn không hợp lệ
        Path filePath = uploadDir.resolve(fileName);

        //Tranh gay tran bo nho
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // Lưu thông tin hình ảnh vào ImageEntity
        ImageEntity imageEntity = ImageEntity.builder()
                .image(fileName)
                .type(file.getContentType())
                .product(product)
                .build();
        return CompletableFuture.completedFuture(getRepo().save(imageEntity));
    }


    public void delete(Long imageId) throws IOException {
        ImageEntity image = getRepo().findById(imageId)
                .orElseThrow(() -> AppException.of(new ErrorModel(HttpStatus.NOT_FOUND.value(), "Khong tim thay hinh anh")));
        Path filePath = Paths.get(uploadPath).resolve(image.getImage());
        Files.deleteIfExists(filePath);
        getRepo().delete(image);
    }

}
