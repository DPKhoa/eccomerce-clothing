package com.app.projectstyleecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class ImageEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    private String image;
    private String type;
    @Lob()
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image_data;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="product_id")
    private ProductEntity product_id;
    

}
