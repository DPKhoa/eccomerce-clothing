package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.exception.AppException;
import com.app.projectstyleecommerce.model.ErrorModel;
import com.app.projectstyleecommerce.model.dto.ProductUpdateDto;
import com.app.projectstyleecommerce.repository.ImageRepository;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.service.ImageService;
import com.app.projectstyleecommerce.service.ProductService;
import io.micrometer.common.util.StringUtils;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
public class ProductServiceImpl extends CommonServiceImpl<ProductEntity,Long, ProductRepository> implements ProductService {
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository repo, ImageRepository imageRepository, ImageService imageService) {
        super(repo);

        this.imageRepository = imageRepository;
        this.imageService = imageService;
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
    @Transactional
    public CompletableFuture<ProductEntity> update(Long id, ProductUpdateDto updateProduct) throws Exception {
        ProductEntity product = getRepo().findById(id)
                .orElseThrow(() -> new Exception("Product is not found"));

        Optional.ofNullable(updateProduct.getProduct_name()).ifPresent(product::setProduct_name);
        Optional.ofNullable(updateProduct.getDescription()).ifPresent(product::setDescription);
        Optional.of(updateProduct.getPrice()).ifPresent(product::setPrice);


        // Không cần xử lý imageList ở đây nữa
        return CompletableFuture.supplyAsync(() -> getRepo().save(product))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    throw AppException.of(new ErrorModel(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Lỗi khi cập nhật: " + throwable.getMessage()
                    ));
                });
    }




}
