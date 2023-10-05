package com.mdbank.api.config;

import com.mdbank.api.domain.Account;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.AccountRepository;
import com.mdbank.api.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        createCustomer();
    }

    private void createCustomer() {
        // Check if the Customer table is empty
        if (customerRepository.count() == 0) {
            // Insert initial data into the Customer table
            Customer customer1 = new Customer();
            customer1.setName("Arisha Barron");
            customerRepository.save(customer1);

            // Create Account 1 for Customer 1
            Account account1 = new Account();
            account1.setCustomer(customer1);
            account1.setAccountNumber("ACCT1");
            account1.setBalance(100.0);
            accountRepository.save(account1);


            Customer customer2 = new Customer();
            customer2.setName("Branden Gibson");
            customerRepository.save(customer2);

            // Create Account 2 for Customer 1
            Account account2 = new Account();
            account2.setCustomer(customer1);
            account2.setAccountNumber("ACCT2");
            account2.setBalance(100.0);
            accountRepository.save(account2);

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

