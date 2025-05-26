package com.test_task.controller;

import com.test_task.model.Purchase;
import com.test_task.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController
{
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService)
    {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getAllPurchases()
    {
        return purchaseService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id)
    {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    public Purchase createPurchase(@RequestBody Purchase purchase)
    {
        return purchaseService.create(purchase);
    }

    @PutMapping("/{id}")
    public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase)
    {
        return purchaseService.update(id, purchase);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id)
    {
        purchaseService.delete(id);
    }

    // Получить сумму наличных продаж по магазину
    @GetMapping("/cash-sum/{storeId}")
    public Double getCashSalesSumByStore(@PathVariable Long storeId)
    {
        return purchaseService.getCashSalesSumByStore(storeId);
    }

    // Лучшие сотрудники по должности за последний год (по количеству и сумме)
    @GetMapping("/best-employees")
    public ResponseEntity<?> getBestEmployeesByPositionLastYear() {
        // Возвращает список: [employeeId, count, sum]
        return ResponseEntity.ok(purchaseService.getBestEmployeesByPositionLastYear());
    }

    // Лучший младший продавец-консультант по продажам умных часов
    @GetMapping("/best-junior-smartwatch")
    public ResponseEntity<?> getBestJuniorSalesBySmartWatches() {
        // Возвращает: [employeeId, count]
        return ResponseEntity.ok(purchaseService.getBestJuniorSalesBySmartWatches());
    }

    // Сумма наличных по всем магазинам за последний год
    @GetMapping("/cash-sum-all-stores")
    public ResponseEntity<?> getCashSumByAllStoresLastYear() {
        // Возвращает Map<storeId, sum>
        return ResponseEntity.ok(purchaseService.getCashSumByAllStoresLastYear());
    }
}