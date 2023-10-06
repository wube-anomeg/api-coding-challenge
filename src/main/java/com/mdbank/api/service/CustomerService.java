package com.mdbank.api.service;


import com.mdbank.api.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;



public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);
}



