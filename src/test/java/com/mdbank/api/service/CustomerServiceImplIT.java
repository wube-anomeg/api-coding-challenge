package com.mdbank.api.service;

import com.mdbank.api.IntegrationTest;
import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationTest
public class CustomerServiceImplIT {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    public void testCreateCustomer() {
        // Create a new customer
        Customer customer = new Customer();
        customer.setName("John");
        customerService.createCustomer(customer);

        // Save the customer using the service
        Customer savedCustomer = customerService.createCustomer(customer);

        // Retrieve the customer from the database using the repository
        Customer retrievedCustomer = customerRepository.findById(savedCustomer.getId()).orElse(null);

        // Assert that the retrieved customer matches the saved customer
        assertNotNull(retrievedCustomer);
        assertEquals("John", retrievedCustomer.getName());
    }
}
