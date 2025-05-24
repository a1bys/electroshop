package com.test_task.controller;

import com.test_task.model.Purchase;
import com.test_task.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return purchaseService.createPurchase(purchase);
    }

    @PutMapping("/{id}")
    public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase)
    {
        return purchaseService.updatePurchase(id, purchase);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id)
    {
        purchaseService.deletePurchase(id);
    }

    // Получить сумму наличных продаж по магазину
    @GetMapping("/cash-sum/{storeId}")
    public Double getCashSalesSumByStore(@PathVariable Long storeId)
    {
        return purchaseService.getCashSalesSumByStore(storeId);
    }
}