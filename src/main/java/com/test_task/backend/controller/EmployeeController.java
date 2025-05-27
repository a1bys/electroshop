package com.test_task.backend.controller;

import com.test_task.backend.model.Employee;
import com.test_task.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Сотрудники", description = "API для управления сотрудниками")
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;

    // получить всех сотрудников
    @Operation(summary = "Создать нового сотрудника")
    @ApiResponse(responseCode = "200", description = "Сотрудник успешно создан")
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee)
    {
        Employee created = employeeService.create(employee);
        return ResponseEntity.ok(created);
    }

    // получить сотрудника по id
    @Operation(summary = "Получить сотрудника по ID")
    @ApiResponse(responseCode = "200", description = "Сотрудник успешно найден")
    @ApiResponse(responseCode = "404", description = "Сотрудник не найден")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id)
    {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // получить всех сотрудников
    @Operation(summary = "Получить всех сотрудников")
    @ApiResponse(responseCode = "200", description = "Список сотрудников успешно получен")
    @GetMapping
    public List<Employee> getAll()
    {
        return employeeService.findAll();
    }

    // Получить топ сотрудников по количеству и сумме продаж за последний год
    @GetMapping("/top-sales-last-year")
    @Operation(summary = "Получить топ сотрудников по продажам за последний год")
    @ApiResponse(responseCode = "200", description = "Топ сотрудников успешно получен")
    public List<EmployeeService.EmployeeSalesStats> getTopSalesLastYear()
    {
        return employeeService.findTopSalesLastYear();
    }

    // Лучший младший продавец-консультант по продажам умных часов за последний год
    @GetMapping("/best-junior-smartwatch-sales")
    @Operation(summary = "Получить лучшего младшего продавца-консультанта по продажам умных часов за последний год")
    @ApiResponse(responseCode = "200", description = "Лучший младший продавец-консультант успешно найден")
    public EmployeeService.EmployeeSalesStats getBestJuniorSmartwatchSalesLastYear()
    {
        return employeeService.findBestJuniorSmartwatchSalesLastYear();
    }

    // получить сотрудников по id отдела
    @PutMapping("/{id}")
    @Operation(summary = "Обновить сотрудника по ID")
    @ApiResponse(responseCode = "200", description = "Сотрудник успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Сотрудник не найден")
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
    @Operation(summary = "Удалить сотрудника по ID")
    @ApiResponse(responseCode = "204", description = "Сотрудник успешно удален")
    @ApiResponse(responseCode = "404", description = "Сотрудник не найден")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}