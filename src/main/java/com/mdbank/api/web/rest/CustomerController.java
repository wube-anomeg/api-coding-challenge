package com.mdbank.api.web.rest;

import com.mdbank.api.domain.Customer;
import com.mdbank.api.service.CustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents the REST API endpoints for managing customers.
 * It provides operations for creating, retrieving, updating and deleting customers.
 */
@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer API", description = "Customer API operations")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Create a new customer
     * @param customer The customer to create
     * @return The created customer
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    /**
     * Retrieve all customers
     * @return A list of all customers
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Retrieve a customer by ID
     * @param customerId The ID of the customer to retrieve
     * @return The customer with the specified ID, or NOT_FOUND if no such customer exists
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Update a customer by ID
     * @param customerId The ID of the customer to update
     * @param updatedCustomer The updated customer information
     * @return The updated customer, or NOT_FOUND if no such customer exists
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Customer updated = customerService.updateCustomer(customerId, updatedCustomer);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * Delete a customer by ID
     * @param customerId The ID of the customer to delete
     * @return NO_CONTENT if the customer was successfully deleted, or NOT_FOUND if no such customer exists
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

