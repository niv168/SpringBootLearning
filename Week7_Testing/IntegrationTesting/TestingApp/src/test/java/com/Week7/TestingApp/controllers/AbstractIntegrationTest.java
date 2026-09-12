package com.Week7.TestingApp.controllers;


import com.Week7.TestingApp.TestContainerConfiguration;
import com.Week7.TestingApp.dto.EmployeeDto;
import com.Week7.TestingApp.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {
    @Autowired
    WebTestClient webTestClient;


    Employee testEmployee=Employee.builder()

                .email("v@gmail.com")
                .name("Vidhi")

                .salary(100L)
                .build();

    EmployeeDto testEmployeeDto=EmployeeDto.builder()

                .email("v@gmail.com")
                .name("Vidhi")

                .salary(100L)
                .build();
}
