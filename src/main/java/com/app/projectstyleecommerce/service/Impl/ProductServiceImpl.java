package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl extends CommonServiceImpl<ProductEntity,Long, ProductRepository> implements ProductService {
    public ProductServiceImpl(ProductRepository repo) {
        super(repo);
    }
    public static final int PRODUCT_MAX_LENGTH = 50;
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
        public ProductEntity update(Long id, Map<String, Object> updateProduct) throws Exception {
            ProductEntity product = getRepo().findById(id).orElseThrow(()-> new Exception("Product is not found"));
            updateProduct.forEach((key, value)->{
              switch (key){
                  case "product_name": product.setProduct_name((String) value);
                  break;
                  case "description": product.setDescription((String) value);
                  break;
                  case "image": product.setImage((String) value);
                  break;
              }
            });
            return getRepo().save(product);
        }
}
