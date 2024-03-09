package com.foo;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class TestcontainersTest {
    @Container
    private static final  PostgreSQLContainer<?> postgreSQLContainer = 
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("foo-dao-unit-test")
                    .withUsername("foo")
                    .withPassword("Password123.");

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
    }
}

