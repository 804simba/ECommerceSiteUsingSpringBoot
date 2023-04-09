package com.timolisa.ecommercesite.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class CartDTO {
    private Long userId;
    private Map<Long, Integer> products;
    private BigDecimal totalPrice;

    public CartDTO(Long userId, BigDecimal totalPrice) {
        this.userId = userId;
        this.products = new HashMap<>();
        this.totalPrice = totalPrice;
    }
}
