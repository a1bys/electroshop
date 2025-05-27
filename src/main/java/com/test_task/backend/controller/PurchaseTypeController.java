package com.test_task.backend.controller;

import com.test_task.backend.model.PurchaseType;
import com.test_task.backend.service.PurchaseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-types")
@Tag(name = "Тип покупки", description = "Управление типами покупок")
public class PurchaseTypeController
{
    private final PurchaseTypeService purchaseTypeService;

    @Autowired
    public PurchaseTypeController(PurchaseTypeService purchaseTypeService)
    {
        this.purchaseTypeService = purchaseTypeService;
    }

    @Operation(summary = "Получить все типы покупок")
    @ApiResponse(responseCode = "200", description = "Список типов покупок успешно получен")
    @GetMapping
    public List<PurchaseType> getAllPurchaseTypes()
    {
        return purchaseTypeService.getAllPurchaseTypes();
    }

    @Operation(summary = "Получить тип покупки по ID")
    @ApiResponse(responseCode = "200", description = "Тип покупки успешно получен")
    @ApiResponse(responseCode = "404", description = "Тип покупки не найден")
    @GetMapping("/{id}")
    public PurchaseType getPurchaseTypeById(@PathVariable Long id)
    {
        return purchaseTypeService.getPurchaseTypeById(id);
    }

    @PostMapping
    @Operation(summary = "Создать новый тип покупки")
    @ApiResponse(responseCode = "201", description = "Тип покупки успешно создан")
    public PurchaseType createPurchaseType(@RequestBody PurchaseType purchaseType)
    {
        return purchaseTypeService.create(purchaseType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип покупки")
    @ApiResponse(responseCode = "200", description = "Тип покупки успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Тип покупки не найден")
    public PurchaseType updatePurchaseType(@PathVariable Long id, @RequestBody PurchaseType purchaseType)
    {
        return purchaseTypeService.update(id, purchaseType);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип покупки")
    @ApiResponse(responseCode = "204", description = "Тип покупки успешно удален")
    @ApiResponse(responseCode = "404", description = "Тип покупки не найден")
    public void deletePurchaseType(@PathVariable Long id)
    {
        purchaseTypeService.delete(id);
    }
}