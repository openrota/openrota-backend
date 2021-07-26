package com.shareNwork.domain.filters;

import lombok.Data;

@Data
public class EmployeeFilter {
    private FilterField firstName;
    private FilterField lastName;
    private FilterField emailId;
}
