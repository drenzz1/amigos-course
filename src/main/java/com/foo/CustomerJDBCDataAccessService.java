package com.foo;

import com.foo.dao.CustomerDao;
import com.foo.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> listAllCustomers() {
        var sql = """
                Select id , name , email , age from customer
                """;

        return jdbcTemplate.query(sql,customerRowMapper);

    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                Select id , name , email , age from customer where id = ?
                """;
        return  jdbcTemplate.query(sql,customerRowMapper,id).stream().findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                Insert into customer (name,email,age)values (?,?,?)\s
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
