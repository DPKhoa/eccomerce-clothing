package com.app.projectstyleecommerce.repository;

import com.app.projectstyleecommerce.entity.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CommonJpaRepository<ProductEntity, Long> {

}
