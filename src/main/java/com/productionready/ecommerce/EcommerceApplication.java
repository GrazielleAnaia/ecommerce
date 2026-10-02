package com.productionready.ecommerce;


import com.productionready.ecommerce.model.Product;
import com.productionready.ecommerce.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Bean
    CommandLineRunner startData(ProductRepository repository) {
        return args -> {
            repository.save(new Product("mouse", 29.99, 150));
            repository.save(new Product("keyboard", 89.99, 75));
            IO.println("Product in DB: " + repository.findAll().size());

        };
    }
}
