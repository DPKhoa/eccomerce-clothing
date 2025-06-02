package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.service.ProductService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UrlUtil.PRODUCT_URL)
public class ProductController extends CommonController<ProductEntity,Long, ProductService> {
    public ProductController(ProductService service) {
        super(service);
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
    public ResponseEntity<ProductEntity> getProductById( @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(getService().findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updateProduct,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {
        ProductEntity updatedProduct = getService().update(id, updateProduct, imageFile);
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) throws Exception {
        getService().deleteById(id);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long id,
            @RequestPart("file") MultipartFile file) throws Exception {
        String imageEntity = getService().uploadImage(id, file);
        return ResponseEntity.ok(imageEntity);
    }
}
