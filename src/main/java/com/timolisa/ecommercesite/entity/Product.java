package com.timolisa.ecommercesite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "products")
@Entity(name = "product")
@NoArgsConstructor
@Setter @Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String category;
    @Column(name = "imageURL", nullable = false, columnDefinition = "text")
    private String imageURL;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
