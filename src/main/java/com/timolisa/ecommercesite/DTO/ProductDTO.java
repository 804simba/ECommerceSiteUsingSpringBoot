package com.timolisa.ecommercesite.DTO;

import com.timolisa.ecommercesite.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String price;
    private String description;
    private Long quantity;
    private String category;
    private String imageURL;

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = String.valueOf(product.getPrice());
        this.description = product.getDescription();
        this.quantity = product.getQuantity();
        this.category = product.getCategory();
        this.imageURL = product.getImageURL();
    }
}
