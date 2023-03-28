package com.timolisa.ecommercesite.Controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.timolisa.ecommercesite.DTO.ProductDTO;
import com.timolisa.ecommercesite.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class DashboardController {
    private final ProductService productService;
    private final Cloudinary cloudinary;

    @Autowired
    public DashboardController(ProductService productService, Cloudinary cloudinary) {
        this.productService = productService;
        this.cloudinary = cloudinary;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("productList", productService.findAllProducts());
        return "dashboard";
    }

    @PostMapping("/save-product")
    public String addProduct(@ModelAttribute("product") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile imageFile,
                             RedirectAttributes redirectAttributes) throws IOException {
//        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
//        Map<>
//        String publicId = (String) uploadResult.get("public_id");
        String publicID = cloudinary.uploader()
                        .upload(imageFile.getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString()))
                                .get("url").toString();
        productDTO.setImageURL(publicID);
        System.out.println("this is my DTO " + productDTO);
        productService.saveAndFlush(productDTO);

        // add success message
        redirectAttributes.addFlashAttribute("successMessage", "Product saved successfully");
        redirectAttributes.addFlashAttribute("products", productService.findAllProducts());
        return "redirect:/dashboard";
    }

}
