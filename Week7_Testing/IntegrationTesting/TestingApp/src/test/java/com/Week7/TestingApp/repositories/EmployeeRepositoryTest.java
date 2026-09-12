package com.Week7.TestingApp.repositories;

import com.Week7.TestingApp.TestContainerConfiguration;
import com.Week7.TestingApp.entities.Employee;
import org.junit.BeforeClass;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;




    private Employee employee;

    @BeforeEach
    void setUp(){
        employee=Employee.builder()

                .name("Vidhi")
                .email("v@gmail.com")
                .salary(100L)
                .build();
    }
    @Test
    void testfindByEmail_whenEmailIsValid_thenReturnEmployee() {

        //Arrange,Given
        employeeRepository.save(employee);

        //Act,When
        List<Employee>employeeList=employeeRepository.findByEmail(employee.getEmail());

        //Assert,Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testfindByEmail_whenEmailIsInvalid_thenReturnEmptyEmployee() {

        String email="emailnotPresent@gmail.com";

        List<Employee> employeeList=employeeRepository.findByEmail(email);

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();

    }
}