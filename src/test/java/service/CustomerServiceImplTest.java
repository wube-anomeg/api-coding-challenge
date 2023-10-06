package service;

import com.mdbank.api.domain.Customer;
import com.mdbank.api.repository.CustomerRepository;
import com.mdbank.api.service.iml.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = customerService.getAllCustomers();

        // Assert
        assertEquals(customers, result);
    }

    @Test
    public void testGetCustomerById_CustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(customerId);

        // Assert
        assertEquals(customer, result);
    }

    @Test
    public void testGetCustomerById_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Customer result = customerService.getCustomerById(customerId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        Customer customerToCreate = new Customer();
        Customer createdCustomer = new Customer();
        when(customerRepository.save(customerToCreate)).thenReturn(createdCustomer);

        // Act
        Customer result = customerService.createCustomer(customerToCreate);

        // Assert
        assertEquals(createdCustomer, result);
    }

    @Test
    public void testUpdateCustomer_CustomerExists() {
        // Arrange
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // Act
        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        // Assert
        assertEquals(updatedCustomer, result);
    }

    @Test
    public void testUpdateCustomer_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(customerId);

        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Act
        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        // Assert
        assertNull(result);
    }

    @Test
    public void testDeleteCustomer() {
        // Arrange
        Long customerId = 1L;

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }
}
