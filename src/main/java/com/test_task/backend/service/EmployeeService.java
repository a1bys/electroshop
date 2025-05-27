package com.test_task.backend.service;

import com.test_task.backend.model.Employee;
import com.test_task.backend.repository.EmployeeRepository;
import com.test_task.backend.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    public static class EmployeeSalesStats
    {
        private final Employee employee;
        private final long salesCount;
        private final double salesSum;

        public EmployeeSalesStats(Employee employee, long salesCount, double salesSum)
        {
            this.employee = employee;
            this.salesCount = salesCount;
            this.salesSum = salesSum;
        }

        public Employee getEmployee()
        {
            return employee;
        }

        public long getSalesCount()
        {
            return salesCount;
        }

        public double getSalesSum()
        {
            return salesSum;
        }
    }

    public static class BestEmployeeByPosition
    {
        private final String positionName;
        private final Employee employeeByCount;
        private final long salesCount;
        private final Employee employeeBySum;
        private final double salesSum;

        public BestEmployeeByPosition(String positionName, Employee employeeByCount, long salesCount, Employee employeeBySum, double salesSum)
        {
            this.positionName = positionName;
            this.employeeByCount = employeeByCount;
            this.salesCount = salesCount;
            this.employeeBySum = employeeBySum;
            this.salesSum = salesSum;
        }

        public String getPositionName()
        {
            return positionName;
        }

        public Employee getEmployeeByCount()
        {
            return employeeByCount;
        }

        public long getSalesCount()
        {
            return salesCount;
        }

        public Employee getEmployeeBySum()
        {
            return employeeBySum;
        }

        public double getSalesSum()
        {
            return salesSum;
        }
    }

    public List<EmployeeSalesStats> findTopSalesLastYear() {
        LocalDate fromDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        List<Object[]> stats = purchaseRepository.findAllSalesByQuantity(fromDate);
        
        return stats.stream()
                .map(arr -> {
                    Long empId = ((Number) arr[0]).longValue();
                    long count = ((Number) arr[1]).longValue();
                    double sum = arr[2] != null ? ((Number) arr[2]).doubleValue() : 0.0;
                    Employee emp = employeeRepository.findById(empId).orElse(null);
                    return emp != null ? new EmployeeSalesStats(emp, count, sum) : null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingLong(EmployeeSalesStats::getSalesCount).reversed())
                .collect(Collectors.toList());
    }

    public EmployeeSalesStats findBestJuniorSmartwatchSalesLastYear() {
        LocalDate fromDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        List<Object[]> stats = purchaseRepository.findBestJuniorSmartwatchSalesLastYear(fromDate);
        
        return stats.stream()
                .map(arr -> {
                    Long empId = ((Number) arr[0]).longValue();
                    long count = ((Number) arr[1]).longValue();
                    Employee emp = employeeRepository.findById(empId).orElse(null);
                    if (emp != null && emp.getPosition() != null && 
                        "младший продавец-консультант".equalsIgnoreCase(emp.getPosition().getName())) {
                        return new EmployeeSalesStats(emp, count, 0.0);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .max(Comparator.comparingLong(EmployeeSalesStats::getSalesCount))
                .orElse(null);
    }

    public List<BestEmployeeByPosition> findBestEmployeesByPositionLastYear() {
        LocalDate fromDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
        List<Object[]> stats = purchaseRepository.findAllSalesByQuantity(fromDate);
        
        // остальной код метода остается без изменений
        Map<Long, double[]> empStats = new HashMap<>();
        for (Object[] arr : stats)
        {
            Long empId = ((Number) arr[0]).longValue();
            long count = ((Number) arr[1]).longValue();
            double sum = arr[2] != null ? ((Number) arr[2]).doubleValue() : 0.0;
            empStats.put(empId, new double[]{count, sum});
        }

        return employeeRepository.findAll().stream()
                .filter(e -> e.getPosition() != null)
                .collect(Collectors.groupingBy(e -> e.getPosition().getName()))
                .entrySet().stream()
                .map(entry ->
                {
                    String posName = entry.getKey();
                    List<Employee> emps = entry.getValue();

                    Employee bestByCount = null;
                    long maxCount = 0;
                    Employee bestBySum = null;
                    double maxSum = 0.0;

                    for (Employee emp : emps)
                    {
                        double[] salesData = empStats.getOrDefault(emp.getId(), new double[]{0, 0});
                        long count = (long) salesData[0];
                        double sum = salesData[1];

                        if (bestByCount == null || count > maxCount)
                        {
                            bestByCount = emp;
                            maxCount = count;
                        }
                        if (bestBySum == null || sum > maxSum)
                        {
                            bestBySum = emp;
                            maxSum = sum;
                        }
                    }

                    return new BestEmployeeByPosition(posName, bestByCount, maxCount, bestBySum, maxSum);
                })
                .collect(Collectors.toList());
    }
}