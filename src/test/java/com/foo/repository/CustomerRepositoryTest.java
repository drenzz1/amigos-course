package com.foo.repository;

import com.foo.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// Qetu lidhet me databazen qe e kom ne docker yml
class CustomerRepositoryTest  extends AbstractTestcontainers  {
    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;
    @BeforeEach
    void setUp(){
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        //Given

        //When

        //Then
    }

    @Test
    void existsCustomerById() {
        //Given

        //When

        //Then
    }
}