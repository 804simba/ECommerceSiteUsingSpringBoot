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
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "product_sequence"
    )
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
    @Lob
    @Column(nullable = false)
    private byte[] image;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
