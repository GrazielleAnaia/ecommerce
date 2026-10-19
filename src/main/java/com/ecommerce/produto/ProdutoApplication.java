package com.ecommerce.produto;

import com.ecommerce.produto.model.Produto;
import com.ecommerce.produto.repository.ProdutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProdutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdutoApplication.class, args);
    }

    @Bean
    CommandLineRunner startData(ProdutoRepository repository){
        return args -> {
            repository.save(new Produto("mouse", new BigDecimal(129.99)));
            repository.save(new Produto("notebook", new BigDecimal(459.99)));

            IO.println("Produto na BD: " + repository.findAll().size());
        };
    }

}
