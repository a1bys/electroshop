package com.test_task.service;

import com.test_task.model.Employee;
import com.test_task.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(Integer id)
    {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll()
    {
        return employeeRepository.findAll();
    }

    public Employee update(Integer id, Employee updatedEmployee)
    {
        return employeeRepository.findById(id)
                .map(employee ->
                {
                    employee.setFirstName(updatedEmployee.getFirstName());
                    employee.setLastName(updatedEmployee.getLastName());
                    employee.setMiddleName(updatedEmployee.getMiddleName());
                    employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
                    employee.setGender(updatedEmployee.getGender());
                    employee.setPosition(updatedEmployee.getPosition());
                    employee.setStore(updatedEmployee.getStore());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void delete(Integer id)
    {
        employeeRepository.deleteById(id);
    }
}

