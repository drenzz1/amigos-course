package com.foo.service;

import com.foo.CustomerRegistrationRequest;
import com.foo.CustomerUpdateRequest;
import com.foo.dao.CustomerDao;
import com.foo.exception.DuplicateResourceException;
import com.foo.exception.RequestValidationException;
import com.foo.exception.ResourceNotFound;
import com.foo.model.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> listAllCustomers(){
        return customerDao.listAllCustomers();
    }
    public Customer getCustomer(Long id)  {
       return  customerDao
               .selectCustomerById(id)
               .orElseThrow(()->new ResourceNotFound("Customer with id [%s] not found ".formatted(id)));
    }
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        if (customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException(
                    "email already exists "
            );
        }
        customerDao.insertCustomer(new Customer(customerRegistrationRequest.name(), customerRegistrationRequest.email(), customerRegistrationRequest.age()));
    }

    public void deleteCustomerById(Long id) {
        if (!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFound("customer with id [%s] not found ".formatted(id));
        }
        customerDao.deleteCustomer(id);
    }

    public void updateCustomer(Long id, CustomerUpdateRequest updateRequest) {

        Customer customer = getCustomer(id);

        boolean change = false;

        if (updateRequest.name()!= null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            change = true;
        }
        if (updateRequest.age()!=null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            change = true;
        }
        if (updateRequest.email()!=null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsPersonWithEmail(updateRequest.email())){
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
        }
        if (!change){
            throw new RequestValidationException(
                    "no data changes found "
            );
        }
        customerDao.updateCustomer(customer);
    }
}
