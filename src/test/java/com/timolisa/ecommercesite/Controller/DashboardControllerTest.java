package com.timolisa.ecommercesite.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Entity.Product;
import com.timolisa.ecommercesite.Services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(DashboardController.class)
class DashboardControllerTest {
    @MockBean
    private ProductService productService;
    @MockBean
    private Cloudinary cloudinary;
    @Autowired
    private MockMvc mvc;
    @Test
    public void shouldRenderDashboardTemplate() throws Exception {
        // Arrange
        Uploader mockUploader = Mockito.mock(Uploader.class);

        when(productService.findAllProducts()).thenReturn(new ArrayList<>());
        when(cloudinary.uploader()).thenReturn(mockUploader);

        // Act and Assert
        mvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("dashboard"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productDTO"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productList"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("successMessage"));
    }

    @Test
    void shouldCreateNewProduct() throws Exception {
        // Mock the MultipartFile
        MockMultipartFile mockImageFile = new MockMultipartFile(
                "productImage", "test.jpg", "image/jpeg", "test data".getBytes());

        // Create a product and map it to a ProductDTO
        Product product = new Product();
        product.setId(1L);
        product.setName("Jeans");
        product.setPrice(new BigDecimal("1228.99"));
        product.setQuantity(100L);
        product.setCategory("Fashion");
        product.setDescription("Nice clothes");

        ProductDTO productDTO = new ProductDTO(product);

        // Mock the ProductService to return the ProductDTO
        when(productService.saveAndFlush(any(ProductDTO.class))).thenReturn(productDTO);
        when(cloudinary.uploader()).thenReturn(Mockito.mock(Uploader.class));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.multipart("/save-product")
                .file(mockImageFile)
                .flashAttr("product", productDTO))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/dashboard"))
                .andReturn();

        // Verify that the ProductService method was called with the correct arguments
        ArgumentCaptor<ProductDTO> argumentCaptor =
                ArgumentCaptor.forClass(ProductDTO.class);
        verify(productService).saveAndFlush(argumentCaptor.capture());
        ProductDTO capturedProductDTO = argumentCaptor.getValue();
        assertEquals("Jeans", capturedProductDTO.getName());
        assertEquals(new BigDecimal("1228.99"), new BigDecimal(capturedProductDTO.getPrice()));
        assertEquals("Fashion", capturedProductDTO.getCategory());
        assertEquals("Jeans", capturedProductDTO.getName());
        assertEquals("Nice clothes", capturedProductDTO.getDescription());

        // Verify that the success message and product list were added to tje flash
        // attributes
        MockHttpServletRequest request = result.getRequest();
        Object productsObj = request.getAttribute("products");
        List<ProductDTO> productList;
        if (productsObj instanceof List) {
             productList = (List<ProductDTO>) productsObj;
        } else {
            productList = null;
        }
        assert productList != null;
        assertEquals(1, productList.size());
        assertEquals("Jeans", productList.get(0).getName());
    }

    @Test
    void editProduct() {
    }

    @Test
    void deleteProduct() {
    }
}