package com.justnino;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }
    @PostMapping
    public Customer addCustomer(@RequestBody CustomerRegistrationRequest request){
        return customerService.addNewCustomer(request);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerId,updateRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
        customerService.deleteCustomerById(customerId);
    }
}
