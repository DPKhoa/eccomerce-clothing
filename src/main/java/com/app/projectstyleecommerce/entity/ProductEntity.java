package com.app.projectstyleecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    @Column()
    private String product_name;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageEntity> images; // Danh sách hình ảnh liên quan

    @Column(name="description")
    private String description;


}
