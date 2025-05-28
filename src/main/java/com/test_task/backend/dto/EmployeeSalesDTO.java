package com.test_task.backend.dto;

import com.test_task.backend.model.Employee;

import java.math.BigDecimal;

public class EmployeeSalesDTO
{
    private final Employee employee;
    private final Long quantitySold;
    private final BigDecimal totalAmount;

    // конструктор, геттеры и сеттеры
    public EmployeeSalesDTO(Employee employee, Long quantitySold, BigDecimal totalAmount)
    {
        this.employee = employee;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public Long getQuantitySold()
    {
        return quantitySold;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }
}