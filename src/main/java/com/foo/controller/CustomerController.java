package com.foo.controller;

import com.foo.CustomerRegistrationRequest;
import com.foo.CustomerUpdateRequest;
import com.foo.model.Customer;
import com.foo.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    public final  CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.listAllCustomers();
    }

    @GetMapping("{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) {
        return customerService.getCustomer(id);
    }
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }
    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id")Integer id){
        customerService.deleteCustomerById(id);
    }
    @PutMapping("{id}")
    public void editCustomer(@PathVariable("id")Integer id,@RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(id,updateRequest);
    }

}
