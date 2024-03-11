package com.foo.repository;

import com.foo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository
        extends JpaRepository<Customer,Long> {
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id );

}
