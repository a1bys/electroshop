package com.test_task.backend.dto;

import com.test_task.backend.model.Employee;

public class EmployeeDTO
{
    private final Employee employee;
    private final long salesCount;

    public EmployeeDTO(Employee employee, long salesCount)
    {
        this.employee = employee;
        this.salesCount = salesCount;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public long getSalesCount()
    {
        return salesCount;
    }
}