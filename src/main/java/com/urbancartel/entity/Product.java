package com.urbancartel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name="image_url" , length=1000)
    private String imageUrl;
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    /*
       fetchtype.lazy means dont load the seller details unless explicitly requested
         optional=false means a product must have a seller associated with it
        fetchtype.eager load everything at the same time
     */
    @JoinColumn(name="supplier_id",nullable=false)
    private Seller seller;
}
