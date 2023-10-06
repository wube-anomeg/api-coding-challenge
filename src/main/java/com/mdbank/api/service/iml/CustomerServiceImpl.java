package com.mdbank.api.service.iml;

import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.CustomerRepository;
import com.mdbank.api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Returns a list of all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Returns the customer with the specified ID, or null if no such customer exists.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID, or null if no such customer exists
     */
    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElse(null);
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer to create
     * @return the newly created customer
     */
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Updates the customer with the specified ID, or returns null if no such customer exists.
     *
     * @param id the ID of the customer to update
     * @param customer the updated customer information
     * @return the updated customer, or null if no such customer exists
     */
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id); // Ensure the correct ID is set
            return customerRepository.save(customer);
        }
        return null; // Customer with the given ID doesn't exist
    }

    /**
     * Deletes the customer with the specified ID.
     *
     * @param id the ID of the customer to delete
     */
    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
