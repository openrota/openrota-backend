package com.shareNwork.service;

import com.shareNwork.domain.Employee;
import com.shareNwork.repository.EmployeeRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ServiceTest {

    @InjectMock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() throws ParseException {

        Employee employee = new Employee();
        employee.id = 10l;
        employee.setEmailId("xyz@gmail.com");
        employee.setFirstName("xyz");

        Employee employee2 = new Employee();
        employee2.id = 10l;
        employee2.setEmailId("abc@gmail.com");
        employee2.setFirstName("abc");

        PanacheQuery<Employee> mock = Mockito.mock(PanacheQuery.class);
        when(mock.list()).thenReturn(Arrays.asList(employee, employee2));

        when(employeeRepository.findAll()).thenReturn(mock);
        when(employeeRepository.create(employee)).thenReturn(employee);
    }

    @Test
    public void testEmployeeQuery() {

        RestAssured.given()
                .when()
                .contentType("application/json")
                .body("{ \"query\": \"{" +
                        "  employee {" +
                        "    firstName" +
                        "    emailId" +
                        "  }" +
                        "}\"" +
                        "}")
                .post("/graphql")
                .then().log().ifValidationFails()
                .statusCode(200)
                .body("data.employee.firstName", Matchers.hasItems("xyz", "abc"))
                .body("data.employee.emailId", Matchers.hasItems("xyz@gmail.com", "abc@gmail.com"));
    }

//    @Test
//    public void testEmployeeMutation() {
//
//        RestAssured.given()
//                .when()
//                .contentType("application/json")
//                .body("{ \"query\": \"mutation {createEmployee(employee: {name: \\\"rishi\\\", email: \\\"rishi@gmail.com\\\"})}\"}")
//                .post("/graphql")
//                .then().log().ifValidationFails()
//                .statusCode(200)
//                .body("data.createEmployee", equalTo(true));
//
//
//    }
}
