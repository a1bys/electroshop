package com.test_task.backend.controller;

import com.test_task.backend.model.PurchaseType;
import com.test_task.backend.service.PurchaseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-types")
public class PurchaseTypeController
{
    private final PurchaseTypeService purchaseTypeService;

    @Autowired
    public PurchaseTypeController(PurchaseTypeService purchaseTypeService)
    {
        this.purchaseTypeService = purchaseTypeService;
    }

    @GetMapping
    public List<PurchaseType> getAllPurchaseTypes()
    {
        return purchaseTypeService.getAllPurchaseTypes();
    }

    @GetMapping("/{id}")
    public PurchaseType getPurchaseTypeById(@PathVariable Long id)
    {
        return purchaseTypeService.getPurchaseTypeById(id);
    }

    @PostMapping
    public PurchaseType createPurchaseType(@RequestBody PurchaseType purchaseType)
    {
        return purchaseTypeService.create(purchaseType);
    }

    @PutMapping("/{id}")
    public PurchaseType updatePurchaseType(@PathVariable Long id, @RequestBody PurchaseType purchaseType)
    {
        return purchaseTypeService.update(id, purchaseType);
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseType(@PathVariable Long id)
    {
        purchaseTypeService.delete(id);
    }
}