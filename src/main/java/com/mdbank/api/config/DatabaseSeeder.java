package com.mdbank.api.config;

import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final CustomerRepository customerRepository;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        // Check if the Customer table is empty
        if (customerRepository.count() == 0) {
            // Insert initial data into the Customer table
            Customer customer1 = new Customer();
            customer1.setName("Arisha Barron");
            customerRepository.save(customer1);

            Customer customer2 = new Customer();
            customer2.setName("Branden Gibson");
            customerRepository.save(customer2);

            Customer customer3 = new Customer();
            customer3.setName("Rhonda Church");
            customerRepository.save(customer3);

            Customer customer4 = new Customer();
            customer4.setName("Georgina Hazel");
            customerRepository.save(customer4);
            logger.info("Database seeding completed. Added initial customer data.");
        } else {
            logger.info("Database seeding not needed. Customer table is not empty.");
        }
        }
    }

