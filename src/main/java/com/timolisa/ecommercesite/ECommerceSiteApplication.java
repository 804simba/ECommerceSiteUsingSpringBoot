package com.timolisa.ecommercesite;

import com.timolisa.ecommercesite.Repository.ProductRepo;
import com.timolisa.ecommercesite.Repository.UserRepo;
import com.timolisa.ecommercesite.entity.Product;
import com.timolisa.ecommercesite.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ECommerceSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceSiteApplication.class, args);
    }
}
