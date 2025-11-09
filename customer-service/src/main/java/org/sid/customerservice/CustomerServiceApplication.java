package org.sid.customerservice;

import org.sid.customerservice.config.CustomerConfigParams;
import org.sid.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.sid.customerservice.entities.Customer;

@SpringBootApplication
@EnableConfigurationProperties(CustomerConfigParams.class)
public class CustomerServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
		return args -> {
			customerRepository.save(
					Customer.builder()
							.name("nisrine").email("nisrine2003@gmail.com")
							.build());
			customerRepository.save(
					Customer.builder()
							.name("aya").email("aya2009@gmail.com")
							.build());
			customerRepository.save(
					Customer.builder()
							.name("mohammed").email("mohammed2011@gmail.com")
							.build());
			customerRepository.findAll().forEach(r ->{
				System.out.println("===================");
				System.out.println(r.getId());
				System.out.println(r.getName());
				System.out.println(r.getEmail());
				System.out.println("===================");
			});

		};
	}

}
