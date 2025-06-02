package com.app.projectstyleecommerce.repository;

import com.app.projectstyleecommerce.entity.ImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CommonJpaRepository<ImageEntity, Long> {

}
