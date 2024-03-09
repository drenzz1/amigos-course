package com.foo;

import com.foo.dao.CustomerDao;
import com.foo.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> listAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """ 
                Insert into customer (name,email,age)values (?,?,?) 
                """;
        int update = jdbcTemplate.update(sql,customer.getName(),customer.getEmail(),customer.getAge());
        System.out.println("jdbcTemplate.update = "+ update);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return false;
    }

    @Override
    public void deleteCustomer(Integer id) {

    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return false;
    }

    @Override
    public void updateCustomer(Customer update) {

    }
}
