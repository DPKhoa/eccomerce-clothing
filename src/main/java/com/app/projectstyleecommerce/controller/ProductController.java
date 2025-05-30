package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.service.ProductService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> product) throws Exception {
        return ResponseEntity.ok(getService().update(id, product));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) throws Exception {
        getService().deleteById(id);
        return ResponseEntity.ok(true);
    }
}
