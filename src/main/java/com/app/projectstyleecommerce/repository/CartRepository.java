package com.app.projectstyleecommerce.repository;

import com.app.projectstyleecommerce.entity.CartEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends CommonJpaRepository<CartEntity, Long> {
    List<CartEntity> findByUserId(Long userId);
}
