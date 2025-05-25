package com.test_task.service;

import com.test_task.model.Employee;
import com.test_task.repository.EmployeeRepository;
import com.test_task.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Employee create(Employee employee)
    {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(Long id)
    {
        return employeeRepository.findById(id);
    }

    public List<Employee> findAll()
    {
        return employeeRepository.findAll();
    }

    public Employee update(Long id, Employee updatedEmployee)
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

    public void delete(Long id)
    {
        employeeRepository.deleteById(id);
    }

    public static class EmployeeSalesStats {
        public final Employee employee;
        public final long salesCount;
        public final double salesSum;
        public EmployeeSalesStats(Employee employee, long salesCount, double salesSum) {
            this.employee = employee;
            this.salesCount = salesCount;
            this.salesSum = salesSum;
        }
    }

    public static class BestEmployeeByPosition {
        public final String positionName;
        public final Employee employeeByCount;
        public final long salesCount;
        public final Employee employeeBySum;
        public final double salesSum;
        public BestEmployeeByPosition(String positionName, Employee employeeByCount, long salesCount, Employee employeeBySum, double salesSum) {
            this.positionName = positionName;
            this.employeeByCount = employeeByCount;
            this.salesCount = salesCount;
            this.employeeBySum = employeeBySum;
            this.salesSum = salesSum;
        }
    }

    public List<EmployeeSalesStats> findTopSalesLastYear()
    {
        LocalDateTime fromDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        List<Object[]> stats = purchaseRepository.findEmployeeSalesStatsSince(fromDate);
        return stats.stream()
                .map(arr ->
                {
                    Integer empId = ((Number) arr[0]).intValue();
                    long count = ((Number) arr[1]).longValue();
                    double sum = arr[2] != null ? ((Number) arr[2]).doubleValue() : 0.0;
                    Employee emp = employeeRepository.findById(empId).orElse(null);
                    return emp != null ? new EmployeeSalesStats(emp, count, sum) : null;
                })
                .filter(e -> e != null)
                .sorted(Comparator.comparingLong((EmployeeSalesStats e) -> e.salesCount).reversed())
                .collect(Collectors.toList());
    }

    public EmployeeSalesStats findBestJuniorSmartwatchSalesLastYear()
    {
        LocalDateTime fromDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        String typeName = "умные часы"; // используйте точное название типа
        String positionName = "младший продавец-консультант"; // используйте точное название должности
        List<Object[]> stats = purchaseRepository.findEmployeeSmartwatchSalesStatsSince(fromDate, typeName);
        return stats.stream()
                .map(arr -> {
                    Integer empId = ((Number) arr[0]).intValue();
                    long count = ((Number) arr[1]).longValue();
                    Employee emp = employeeRepository.findById(empId).orElse(null);
                    if (emp != null && emp.getPosition() != null && positionName.equalsIgnoreCase(emp.getPosition().getName())) {
                        return new EmployeeSalesStats(emp, count, 0.0);
                    }
                    return null;
                })
                .filter(e -> e != null)
                .max(Comparator.comparingLong(e -> e.salesCount))
                .orElse(null);
    }

    public List<BestEmployeeByPosition> findBestEmployeesByPositionLastYear() {
        java.time.LocalDateTime fromDate = java.time.LocalDateTime.now().minusYears(1);
        List<Object[]> stats = purchaseRepository.findEmployeeSalesStatsSince(fromDate);
        // employeeId -> [count, sum]
        java.util.Map<Long, long[]> empStats = new java.util.HashMap<>();
        java.util.Map<Long, Double> empSum = new java.util.HashMap<>();
        for (Object[] arr : stats) {
            Long empId = ((Number) arr[0]).longValue();
            long count = ((Number) arr[1]).longValue();
            double sum = arr[2] != null ? ((Number) arr[2]).doubleValue() : 0.0;
            empStats.put(empId, new long[]{count});
            empSum.put(empId, sum);
        }
        // Группировка по должности
        return employeeRepository.findAll().stream()
            .filter(e -> e.getPosition() != null)
            .collect(java.util.stream.Collectors.groupingBy(e -> e.getPosition().getName()))
            .entrySet().stream()
            .map(entry -> {
                String pos = entry.getKey();
                List<Employee> emps = entry.getValue();
                Employee bestByCount = null;
                long maxCount = 0;
                Employee bestBySum = null;
                double maxSum = 0.0;
                for (Employee e : emps) {
                    Long id = e.getId().longValue();
                    long count = empStats.getOrDefault(id, new long[]{0})[0];
                    double sum = empSum.getOrDefault(id, 0.0);
                    if (bestByCount == null || count > maxCount) {
                        bestByCount = e;
                        maxCount = count;
                    }
                    if (bestBySum == null || sum > maxSum) {
                        bestBySum = e;
                        maxSum = sum;
                    }
                }
                return new BestEmployeeByPosition(pos, bestByCount, maxCount, bestBySum, maxSum);
            })
            .collect(java.util.stream.Collectors.toList());
    }
}
