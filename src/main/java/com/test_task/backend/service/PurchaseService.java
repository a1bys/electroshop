package com.test_task.backend.service;

import com.test_task.backend.dto.EmployeeDTO;
import com.test_task.backend.dto.EmployeeSalesDTO;
import com.test_task.backend.model.Employee;
import com.test_task.backend.model.Purchase;
import com.test_task.backend.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseService
{
    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase create(Purchase purchase)
    {
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> findById(Long id)
    {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> findAll()
    {
        return purchaseRepository.findAll();
    }

    public Purchase update(Long id, Purchase updatedPurchase)
    {
        return purchaseRepository.findById(id)
                .map(purchase ->
                {
                    purchase.setProduct(updatedPurchase.getProduct());
                    purchase.setEmployee(updatedPurchase.getEmployee());
                    purchase.setStore(updatedPurchase.getStore());
                    purchase.setTimestamp(updatedPurchase.getTimestamp());
                    purchase.setPurchaseType(updatedPurchase.getPurchaseType());
                    return purchaseRepository.save(purchase);
                })
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public void delete(Long id)
    {
        purchaseRepository.deleteById(id);
    }

    public List<Purchase> getAllPurchases()
    {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id)
    {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    @Transactional(readOnly = true)
    public List<EmployeeSalesDTO> getBestEmployeesByPosition(Long positionId) {
        LocalDate yearAgo = LocalDate.now().minusYears(1);

        // По количеству продаж
        List<Object[]> quantityResults = purchaseRepository.findBestEmployeesByPositionAndQuantity(
                positionId, yearAgo);

        // По сумме продаж
        List<Object[]> amountResults = purchaseRepository.findBestEmployeesByPositionAndAmount(
                positionId, yearAgo);

        return combineResults(quantityResults, amountResults);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getBestJuniorConsultantSmartWatch() {
        Object[] result = purchaseRepository.findBestJuniorConsultantSmartWatchSales();
        if (result == null) {
            return null;
        }

        Employee employee = (Employee) result[0];
        Long salesCount = (Long) result[1];

        return new EmployeeDTO(employee, salesCount);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalCashPayments() {
        return purchaseRepository.findTotalCashPayments();
    }

    // Сумма наличных по всем магазинам за последний год
    public Map<Long, Double> getCashSumByAllStoresLastYear()
    {
        return new HashMap<>();
    }

    private List<EmployeeSalesDTO> combineResults(List<Object[]> quantityResults, List<Object[]> amountResults)
    {
        Map<Employee, EmployeeSalesDTO> resultMap = new HashMap<>();

        // Обработка результатов по количеству продаж
        for (Object[] result : quantityResults)
        {
            Employee employee = (Employee) result[0];
            Long quantity = (Long) result[1];
            BigDecimal amount = (BigDecimal) result[2];

            resultMap.put(employee, new EmployeeSalesDTO(employee, quantity, amount));
        }

        // Обработка результатов по сумме продаж
        for (Object[] result : amountResults)
        {
            Employee employee = (Employee) result[0];
            Long quantity = (Long) result[1];
            BigDecimal amount = (BigDecimal) result[2];

            if (resultMap.containsKey(employee))
            {
                EmployeeSalesDTO existing = resultMap.get(employee);
                // Сравниваем сумму продаж
                if (amount.compareTo(existing.getTotalAmount()) > 0)
                {
                    resultMap.put(employee, new EmployeeSalesDTO(employee, quantity, amount));
                }
            } else
            {
                resultMap.put(employee, new EmployeeSalesDTO(employee, quantity, amount));
            }
        }

        // Сортируем по сумме продаж в порядке убывания
        return resultMap.values().stream().sorted(Comparator.comparing(EmployeeSalesDTO::getTotalAmount).thenComparing(EmployeeSalesDTO::getTotalAmount).thenComparing(EmployeeSalesDTO::getQuantitySold).reversed()).collect(Collectors.toList());
    }
}