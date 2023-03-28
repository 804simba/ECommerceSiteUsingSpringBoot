package com.timolisa.ecommercesite.Services.implementations;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Entity.Product;
import com.timolisa.ecommercesite.ServiceImpl.ProductServiceImpl;
import com.timolisa.ecommercesite.Repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepo productRepo;
    @Test
    void save() {
        // create a new ProductDTO object
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Tim");
        productDTO.setPrice(BigDecimal.valueOf(12290.77));
        productDTO.setDescription("good");
        productDTO.setCategory("Female");
        productDTO.setImageURL("hyddkic");

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImageURL(productDTO.getImageURL());

        // Mock the save method of the product Repository
        // to return the saved product object
        when(productRepo.save(product)).thenReturn(product);

        // then call the save method of the productService
        // implementation with the productDTO object
        ProductDTO savedProductDTO = productService.save(productDTO);
        // Verify that the save method was called on the ProductRepo repository with the same Product object
        verify(productRepo, times(1)).save(product);

        // Verify that the returned ProductDTO object is not null
        assertNotNull(savedProductDTO);
    }
}