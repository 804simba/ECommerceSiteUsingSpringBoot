package com.timolisa.ecommercesite.DTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
}
