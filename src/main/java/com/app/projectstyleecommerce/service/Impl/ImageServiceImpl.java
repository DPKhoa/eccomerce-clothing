package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.repository.ImageRepository;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl extends CommonServiceImpl<ImageEntity,Long, ImageRepository> implements ImageService {

    @Autowired
    private ProductRepository productRepository;
    public ImageServiceImpl(ImageRepository repo) {
        super(repo);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        ProductEntity product = new ProductEntity();
        if(productRepository.findById(product.getId()).isPresent()){
            product = productRepository.findById(product.getId()).get();
        }
        else{
            throw new ChangeSetPersister.NotFoundException("Khong tim thay san pham");
        }
        ImageEntity imageEntity = getRepo().save(
                ImageEntity.builder().name()
        )
    }

}
