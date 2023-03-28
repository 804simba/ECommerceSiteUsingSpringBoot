package com.timolisa.ecommercesite;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ECommerceSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceSiteApplication.class, args);
    }
}
@Configuration
class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
@Configuration
class CloudinaryConfiguration {
    private final String CLOUD_NAME = "dgprtrm4b";
    private final String API_KEY = "688969496941139";
    private final String API_SECRET = "XDyY5rSlVgeKAG8jxzuRunLAclo";

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);
    }
}