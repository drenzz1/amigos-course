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
    public Optional<Customer> selectCustomerById(Long id) {
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
        var sql = """
                Select count(id) from customer  where email = ? 
                """;
        Integer count =  jdbcTemplate.queryForObject(sql, Integer.class,email);
        return count!= null && count > 0;
    }

    @Override
    public void deleteCustomer(Long id) {
        var sql = """
                Delete from customer where id=?
                """;
        int result = jdbcTemplate.update(sql,id);
        System.out.println("DeleteCustomerById result : "+result);

    }

    @Override
    public boolean existsPersonWithId(Long id) {
        var sql = """
                Select count(id) from customer  where id = ? 
                """;
        Integer count =  jdbcTemplate.queryForObject(sql, Integer.class,id);
        return count!= null && count > 0;
    }

    @Override
    public void updateCustomer(Customer update) {
        if (update.getName()!=null){
            String sql ="Update customer SET name=? where id = ?";
            int result = jdbcTemplate.update(sql,update.getName(),update.getId());
            System.out.println("Update customer name result = "+result);
        }
        if (update.getAge()!=null){
            String sql = "Update customer SEt age = ? where id =?";
            int result = jdbcTemplate.update(sql,update.getAge(),update.getId());
            System.out.println("Update customer age result = "+result);

        }
        if (update.getEmail()!=null){
            String sql = "Update customer Set email = ? where id = ?";
            int result = jdbcTemplate.update(sql,update.getEmail(),update.getId());
            System.out.println("Update customer email result =" +result );
        }
    }
}
