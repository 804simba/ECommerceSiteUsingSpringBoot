package com.timolisa.ecommercesite.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "imageURL", columnDefinition = "text")
    private String imageURL;
    @ManyToMany(mappedBy = "products")
    private Set<Order> orders = new HashSet<>();

    public Product(String name, BigDecimal price, String description, String category, String imageURL) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageURL = imageURL;
    }
}
