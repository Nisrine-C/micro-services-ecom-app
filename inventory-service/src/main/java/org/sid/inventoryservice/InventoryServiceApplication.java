package org.sid.inventoryservice;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            productRepository.save(
                    Product.builder()
                            .id("P001")
                            .name("Laptop")
                            .unitPrice(999.99)
                            .quantity(10)
                            .build());

            productRepository.save(
                    Product.builder()
                            .id("P002")
                            .name("Smartphone")
                            .unitPrice(499.99)
                            .quantity(25)
                            .build());

            productRepository.save(
                    Product.builder()
                            .id("P003")
                            .name("Headphones")
                            .unitPrice(79.99)
                            .quantity(50)
                            .build());

            productRepository.save(
                    Product.builder()
                            .id("P004")
                            .name("Tablet")
                            .unitPrice(299.99)
                            .quantity(15)
                            .build());

            productRepository.save(
                    Product.builder()
                            .id("P005")
                            .name("Smart Watch")
                            .unitPrice(199.99)
                            .quantity(30)
                            .build());
            productRepository.findAll().forEach(p ->{
                System.out.println("===================");
                System.out.println("Product ID: " + p.getId());
                System.out.println("Name: " + p.getName());
                System.out.println("unitPrice: $" + p.getUnitPrice());
                System.out.println("Quantity: " + p.getQuantity());
                System.out.println("===================");
            });

        };
    }


}
