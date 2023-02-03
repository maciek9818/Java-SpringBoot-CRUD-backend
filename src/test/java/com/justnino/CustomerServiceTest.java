package com.justnino;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock private CustomerRepository customerRepository;
    @Captor
    private ArgumentCaptor<Customer> customerCaptor;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerRepository);
    }
    @Test
    void canGetAllCustomers() {
        //when
        underTest.getAllCustomers();
        //then
        verify(customerRepository).findAll();

    }

    @Test
    void canAddNewCustomer() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Tester", "tester@gmail.com", 30);
        Customer customer = new Customer("Tester", "tester@gmail.com", 30);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = underTest.addNewCustomer(request);
        verify(customerRepository).save(customerCaptor.capture());
        assertEquals(customer, customerCaptor.getValue());
        assertEquals(customer, result);
    }

    @Test
    void canUpdateCustomerCustomerFound() {
        Integer customerId = 1;
        CustomerUpdateRequest request = new CustomerUpdateRequest("Tester", "tester@gmail.com", 30);
        Customer customer = new Customer("John", "john@gmail.com", 22);
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));

        underTest.updateCustomer(customerId, request);
        verify(customerRepository).save(customer);
        assertEquals("Tester",customer.getName());
        assertEquals("tester@gmail.com",customer.getEmail());
        assertEquals(30,customer.getAge());

    }
    @Test
    void canUpdateCustomerNotCustomerFound() {
        Integer customerId = 1;
        CustomerUpdateRequest request = new CustomerUpdateRequest("Tester", "tester@gmail.com", 30);
        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> underTest.updateCustomer(customerId, request));
        assertEquals("Customer not found for this id :: 1", exception.getMessage());

    }

    @Test
    void testDeleteCustomerById() {
        Integer customerId = 1;
        underTest.deleteCustomerById(customerId);
        verify(customerRepository).deleteById(customerId);
    }
}