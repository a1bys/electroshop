package com.test_task.controller;

import com.test_task.model.Employee;
import com.test_task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    // получить всех сотрудников
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee)
    {
        Employee created = employeeService.create(employee);
        return ResponseEntity.ok(created);
    }

    // получить сотрудника по id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id)
    {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // получить всех сотрудников
    @GetMapping
    public List<Employee> getAll()
    {
        return employeeService.findAll();
    }

    // Получить топ сотрудников по количеству и сумме продаж за последний год
    @GetMapping("/top-sales-last-year")
    public List<EmployeeService.EmployeeSalesStats> getTopSalesLastYear()
    {
        return employeeService.findTopSalesLastYear();
    }

    // Лучший младший продавец-консультант по продажам умных часов за последний год
    @GetMapping("/best-junior-smartwatch-sales")
    public EmployeeService.EmployeeSalesStats getBestJuniorSmartwatchSalesLastYear()
    {
        return employeeService.findBestJuniorSmartwatchSalesLastYear();
    }

    // получить сотрудников по id отдела
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee)
    {
        try
        {
            Employee updated = employeeService.update(id, employee);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    // удалить сотрудника по id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

