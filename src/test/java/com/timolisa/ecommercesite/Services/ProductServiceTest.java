package com.timolisa.ecommercesite.Services;

import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Entity.Product;
import com.timolisa.ecommercesite.Exception.ProductNotFoundException;
import com.timolisa.ecommercesite.Repository.ProductRepository;
import com.timolisa.ecommercesite.ServiceImpl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void shouldFindAllProducts() {
        // Create a list of products
        List<Product> productList = Arrays.asList(
                new Product("Product 1", new BigDecimal("10.00"), "Description 1", "Category 1", "image1.jpg"),
                new Product("Product 2", new BigDecimal("20.00"), "Description 2", "Category 2", "image2.jpg"),
                new Product("Product 3", new BigDecimal("30.00"), "Description 3", "Category 3", "image3.jpg")
        );
        // Mock the productRepository to return the above list of products
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> productDTOList = productService.findAllProducts();
        assertNotNull(productDTOList);
        assertEquals(3, productList.size());

    }
    @Test
    public void shouldFindProductById() throws ProductNotFoundException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));
        product.setDescription("Description 1");
        product.setCategory("Category 1");
        product.setImageURL("image1.jpg");

        // save the mock product to the Repository
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Create a ProductDTO object and configure the modelMapper mock to return it
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Product 1");
        productDTO.setPrice("10.00");
        productDTO.setDescription("Description 1");
        productDTO.setCategory("Category 1");
        productDTO.setImageURL("image1.jpg");
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);


        // Mock the product repository to return the above product when findByID is called

        ProductDTO returnedProduct = productService.findProductById(1L);
        assertNotNull(productDTO);
        assertEquals("Product 1", returnedProduct.getName());
        assertEquals(new BigDecimal("10.00"), new BigDecimal(returnedProduct.getPrice()));
        assertEquals("Description 1", returnedProduct.getDescription());
        assertEquals("Category 1", returnedProduct.getCategory());
        assertEquals("image1.jpg", returnedProduct.getImageURL());
    }
}
