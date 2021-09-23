package com.shareNwork.service;

import com.shareNwork.domain.Employee;
import com.shareNwork.repository.EmployeeRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ServiceTest {

    @InjectMock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() throws ParseException {
        Employee employee = new Employee();
        employee.setEmailId("xyz@gmail.com");
        employee.setFirstName("xyz");

        Employee employee3 = new Employee();
        employee3.setEmailId("xyz@gmail.com");
        employee3.setFirstName("xyz");

        Employee employee2 = new Employee();
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

    @Test
    public void createEmployee() {

        String requestBody =
                "{\"query\":" +
                        "\"" +
                        "mutation createEmployee { " +
                        "createEmployee" +
                        "(employee: " +
                        "{"+
                        "firstName: \\\"xyz\\\" " +
                        "emailId: \\\"xyz@gmail.com\\\" " +
                        "}" +
                        ")" +
                        "{" +
                        "firstName " +
                        "emailId" +
                        "}" +
                        "}" +
                        "\"" +
                        "}";

        RestAssured.given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .post("/graphql/")
                .then()
                .contentType(ContentType.JSON)
                .body("data.createEmployee.firstName", Matchers.is("xyz"))
                .body("data.createEmployee.emailId", Matchers.is("xyz@gmail.com"))
                .statusCode(200);

    }

}
