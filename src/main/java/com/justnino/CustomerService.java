package com.justnino;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer addNewCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        return customerRepository.save(customer);
    }

    public void updateCustomer(Integer customerId,CustomerUpdateRequest customerUpdateRequest){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for this id :: " + customerId));

        customer.setName(customerUpdateRequest.name());
        customer.setEmail(customerUpdateRequest.email());
        customer.setAge(customerUpdateRequest.age());
        customerRepository.save(customer);
    }

    public void deleteCustomerById(Integer customerId){
        customerRepository.deleteById(customerId);
    }
}
