package ru.anikeeva.bank.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.anikeeva.bank.dto.ClientDTO;
import ru.anikeeva.bank.service.ClientService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest

public class ClientControllerTest {
    @Container
    private static final PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:latest")
                    .withDatabaseName("bank")
                    .withUsername("postgres")
                    .withPassword("ytpkbvtyz");
    @Autowired
    private ClientService clientService;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void startContainers() {
        postgres.start();
    }

    @AfterEach
    void stopContainers() {
        postgres.stop();
    }

    @Test
    void createClient() {
        ClientDTO clientDTOExpected = clientService.createClient(new ClientDTO(
                5L,"Irina Holodova",
                "+79523456789", new BigDecimal("123456.78")));

        ClientDTO clientDTOActual = clientService.getClientById(5L);
        assertEquals(clientDTOExpected.getId(), clientDTOActual.getId());
        assertEquals(clientDTOExpected.getName(), clientDTOActual.getName());
        assertEquals(clientDTOExpected.getPhoneNumber(), clientDTOActual.getPhoneNumber());
        assertEquals(clientDTOExpected.getBalance(), clientDTOActual.getBalance());
    }
}
