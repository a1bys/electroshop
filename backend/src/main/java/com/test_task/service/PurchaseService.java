package com.test_task.service;

import com.test_task.model.Purchase;
import com.test_task.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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

    public Double getCashSalesSumByStore(Long storeId)
    {
        // ВАЖНО: используйте точное название типа оплаты, как оно хранится в базе (например, "наличные")
        String purchaseTypeName = "наличные";
        return purchaseRepository.findCashSalesSumByStore(storeId, purchaseTypeName);
    }

    // Лучшие сотрудники по должности за последний год (по количеству и сумме)
    public List<Object[]> getBestEmployeesByPositionLastYear()
    {
        LocalDateTime fromDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        return purchaseRepository.findEmployeeSalesStatsSince(fromDate);
    }

    // Лучший младший продавец-консультант по продажам умных часов за последний год
    public Object[] getBestJuniorSalesBySmartWatches()
    {
        LocalDateTime fromDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        List<Object[]> stats = purchaseRepository.findEmployeeSmartwatchSalesStatsSince(fromDate, "умные часы");
        Object[] best = null;
        long max = 0;
        for (Object[] row : stats) {
            Long count = (Long) row[1];
            if (count > max) {
                max = count;
                best = row;
            }
        }
        return best;
    }

    // Сумма наличных по всем магазинам за последний год
    public Map<Long, Double> getCashSumByAllStoresLastYear()
    {
        return new HashMap<>();
    }
}