package com.foo.daoaccess;

import com.foo.dao.CustomerDao;
import com.foo.model.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("dao")
public class CustomerDaoAccess implements CustomerDao {
    private static List<Customer>customers;
    static {
        customers=new ArrayList<>();
        Customer alex= new Customer(1L,"Alex","alex@gmail.com",21);
        Customer jamila= new Customer(2L,"Jamila","jamila@gmail.com",19);
        customers.add(alex);
        customers.add(jamila);
    }
    @Override
    public List<Customer> listAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers
                .stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream().anyMatch(c->c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomer(Long id) {
        customers.stream()
                .filter(c->c.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public boolean existsPersonWithId(Long id) {
        return customers.stream().anyMatch(c->c.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }
}
