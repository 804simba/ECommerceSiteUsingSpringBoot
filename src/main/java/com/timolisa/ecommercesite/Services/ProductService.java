package com.timolisa.ecommercesite.Services;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductDTO> findAllProducts();
    ProductDTO findProductById(Long id) throws ProductNotFoundException;
    ProductDTO saveAndFlush(ProductDTO productDTO);
    boolean deleteByID(Long id);
}
