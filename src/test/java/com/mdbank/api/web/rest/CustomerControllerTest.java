package com.mdbank.api.web.rest;

import com.mdbank.api.domain.Customer;
import com.mdbank.api.service.CustomerService;
import com.mdbank.api.web.rest.CustomerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer_Success() {
        // Arrange
        Customer inputCustomer = new Customer();
        inputCustomer.setId(1L);

        Customer createdCustomer = new Customer();
        createdCustomer.setId(1L);

        when(customerService.createCustomer(inputCustomer)).thenReturn(createdCustomer);

        // Act
        ResponseEntity<Customer> responseEntity = customerController.createCustomer(inputCustomer);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdCustomer, responseEntity.getBody());
    }

    @Test
    public void testGetAllCustomers_Success() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        // Act
        ResponseEntity<List<Customer>> responseEntity = customerController.getAllCustomers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customers, responseEntity.getBody());
    }

    @Test
    public void testGetCustomerById_CustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(customer);

        // Act
        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customer, responseEntity.getBody());
    }

    @Test
    public void testGetCustomerById_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;

        when(customerService.getCustomerById(customerId)).thenReturn(null);

        // Act
        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(customerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateCustomer_CustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer inputCustomer = new Customer();
        inputCustomer.setId(customerId);

        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);
        when(customerService.updateCustomer(customerId, inputCustomer)).thenReturn(inputCustomer);

        // Act
        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(customerId, inputCustomer);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(inputCustomer, responseEntity.getBody());
    }
    @Test
    public void testUpdateCustomer_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        Customer inputCustomer = new Customer();
        inputCustomer.setId(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(null);

        // Act
        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(customerId, inputCustomer);

        // Assert

    }

    @Test
    public void testDeleteCustomer_CustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(existingCustomer);

        // Act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(customerId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
    @Test
    public void testDeleteCustomer_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;

        when(customerService.getCustomerById(customerId)).thenReturn(null);

        // Act
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(customerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(customerService, never()).deleteCustomer(customerId);
    }

}
