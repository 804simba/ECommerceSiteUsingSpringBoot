package com.timolisa.ecommercesite.repository;

import com.timolisa.ecommercesite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
