package com.shareNwork.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

