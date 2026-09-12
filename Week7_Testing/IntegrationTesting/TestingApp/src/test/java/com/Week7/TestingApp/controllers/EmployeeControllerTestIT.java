package com.Week7.TestingApp.controllers;

import com.Week7.TestingApp.TestContainerConfiguration;
import com.Week7.TestingApp.dto.EmployeeDto;
import com.Week7.TestingApp.entities.Employee;
import com.Week7.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;


@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //BECAUSE OTHERWISE ITL THROW ERROR AS WEBCLIENT DOESNT WORK IN MOCK ENV BUT NEEDS REAL SERVER
class EmployeeControllerTestIT extends AbstractIntegrationTest {



    @Autowired
    private EmployeeRepository employeeRepository;



    @BeforeEach
    void setUp(){

        employeeRepository.deleteAll();//keep deleteimg repo after every test
    }

    @Test
    void testGetEmployeeById_success(){
        Employee savedEmployee=employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId());


    }

    @Test
    void testGetEmployeeById_Failure(){
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_IfEmployeeAlreadyExists_ThenThrowException(){
        Employee savedEmployee=employeeRepository.save(testEmployee);
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testCreateNewEmployee_IfEmployeeNotExists_success(){
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeNotExists_thenThrowException(){
        webTestClient.put()
                .uri("/employees/999")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateEmployee_whenTryingUpdateEmail_ThenThrowException(){
        Employee saved=employeeRepository.save(testEmployee);
        testEmployeeDto.setEmail("a@gmail.com");
        webTestClient.put()
                .uri("/employees/{id}",saved.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateEmployee_whenEmployeeExists_ThenUpdate(){
        Employee saved=employeeRepository.save(testEmployee);
        testEmployeeDto.setName("vid");
        testEmployeeDto.setSalary(300L);
        testEmployeeDto.setId(saved.getId());
        webTestClient.put()
                .uri("/employees/{id}",saved.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(testEmployeeDto);

    }

    @Test
    void testDeleteEmployee_WhenNoEmployee_ThenThrowException(){
        webTestClient.delete()
                .uri("employees/998")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_WhenEmployeeExists_ThenDelete(){

        Employee saved=employeeRepository.save(testEmployee);
        webTestClient.delete()
                .uri("/employees/{id}",saved.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }

}