package com.timolisa.ecommercesite.Services;

import com.timolisa.ecommercesite.DTO.ProductDTO;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductDTO> findAllProducts();
    Optional<ProductDTO> findProductById(Long id);
    ProductDTO saveAndFlush(ProductDTO productDTO);
    boolean deleteByID(Long id);
}
