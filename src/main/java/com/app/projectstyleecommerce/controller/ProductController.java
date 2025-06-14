package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.model.dto.ProductUpdateDto;
import com.app.projectstyleecommerce.service.ImageService;
import com.app.projectstyleecommerce.service.ProductService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(UrlUtil.PRODUCT_URL)
public class ProductController extends CommonController<ProductEntity,Long, ProductService> {
    private final ImageService imageService;

    public ProductController(ProductService service, ImageService imageService) {
        super(service);
        this.imageService = imageService;
    }

    @PostMapping()
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) throws Exception {
        return ResponseEntity.ok(getService().save(product));
    }

    @GetMapping()
    public List<ProductEntity> getAll() {
        return getService().findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(getService().findById(id));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<ProductEntity>> updateProduct(
            @PathVariable Long id,
            @RequestPart ProductUpdateDto updateProduct,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {
        return getService().update(id, updateProduct, imageFile)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) throws Exception {
        getService().deleteById(id);
        return ResponseEntity.ok(true);
    }

// upload Image
    @PostMapping("/{id}/image")
    @Async
    public CompletableFuture<ResponseEntity<?>> uploadImageAsync(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                CompletableFuture<ImageEntity> imageEntity = imageService.uploadImageAsync(id, file); // Gọi service đồng bộ
                return ResponseEntity.ok(imageEntity);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
            }
        });
    }
    //Delete image
    @DeleteMapping("/{id}/image/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long id,
            @PathVariable Long imageId) throws IOException {
        imageService.delete(imageId);
        return ResponseEntity.noContent().build();
    }

}
