package com.app.projectstyleecommerce.model.dto;

import lombok.Data;

@Data
public class ProductUpdateDto {
    private String product_name;
    private String description;
    private int price;
}
