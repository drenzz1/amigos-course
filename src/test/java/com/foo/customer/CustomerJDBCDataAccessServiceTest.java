package com.foo.customer;

import com.foo.AbstractTestcontainers;
import com.foo.CustomerJDBCDataAccessService;
import com.foo.CustomerRowMapper;
import com.foo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest  extends AbstractTestcontainers {
    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();


    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void listAllCustomers() {
        //Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(customer);
        //When
        List<Customer> customers = underTest.listAllCustomers();
        //Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customer);
        Long id = underTest.listAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        //Then
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            System.out.println();
        });
    }
    @Test
    void willReturnEmptyWhenSelectCustomerById(){
        //Given
        Long id = -1L;
        //When
        var actual =underTest.selectCustomerById(id);
        //Then
        assertThat(actual).isEmpty();


    }



    @Test
    void existsPersonWithEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customer);


        //When
        boolean actual = underTest.existsPersonWithEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;

        //When
        boolean actual = underTest.existsPersonWithEmail(email);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long id = underTest.listAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        underTest.deleteCustomer(id);
        //Then
        var actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void existsCustomerWithId() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long id = underTest.listAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = underTest.existsPersonWithId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithIdWillReturnFalseWhenIdNotPresenet() {
        //Given
        Long id = -1L;

        //When
        var actual = underTest.existsPersonWithId(id);


        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomer() {
        //Given
        String email = FAKER.internet().safeEmailAddress()+ " - "+UUID.randomUUID() ;
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        Long id = underTest.listAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "foo";
        //When
        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);
        //Then
        var actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(
                customer1 -> {
                    assertThat(customer1.getId()).isEqualTo(id);
                    assertThat(customer1.getName()).isEqualTo(newName);
                    assertThat(customer1.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(customer1.getAge()).isEqualTo(customer.getAge());
                }
        );

    }
}