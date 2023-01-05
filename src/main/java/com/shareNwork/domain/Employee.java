package com.shareNwork.domain;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = false)
public class Employee extends PanacheEntity {

    private String firstName;

    private String lastName;

    private String employeeId;

    private String emailId;

    private String designation;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EmployeeRoles",
            joinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"))
    private Set<Role> roles;
    public Employee() {
    }
    public Employee(String firstName, String lastName, String employeeId, String emailId, String designation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.emailId = emailId;
        this.designation = designation;
    }

}

