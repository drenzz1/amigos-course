package com.foo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest // Qita skena nevoj me perdor kurr , se neve sna interesojn shum beans qysh funksionojn edhe testimin e tyre
public class TestcontainersTest extends AbstractTestcontainers {

    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();

    }

}

