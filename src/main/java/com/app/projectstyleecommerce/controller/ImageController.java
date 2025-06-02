package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.ImageEntity;
import com.app.projectstyleecommerce.service.ImageService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping(UrlUtil.FILE_URL)
public class ImageController extends CommonController<ImageEntity, Long, ImageService> {


    public ImageController(ImageService service) {
        super(service);
    }

    @PostMapping("/{upload-file}")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("product_id") Long productId ) throws IOException {
        String uploadImage = getService().uploadImage(file, productId);
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }
}
