package com.test_task.backend.controller;

import com.test_task.backend.dto.EmployeeDTO;
import com.test_task.backend.dto.EmployeeSalesDTO;
import com.test_task.backend.model.Purchase;
import com.test_task.backend.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchases")
@Tag(name = "Покупки", description = "API для управление покупками")
public class PurchaseController
{
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService)
    {
        this.purchaseService = purchaseService;
    }

    @Operation(summary = "Получить все покупки")
    @ApiResponse(responseCode = "200", description = "Список всех покупок")
    @GetMapping
    public List<Purchase> getAllPurchases()
    {
        return purchaseService.getAllPurchases();
    }

    @Operation(summary = "Получить покупку по ID")
    @ApiResponse(responseCode = "200", description = "Покупка найдена")
    @ApiResponse(responseCode = "404", description = "Покупка не найдена")
    @GetMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id)
    {
        return purchaseService.getPurchaseById(id);
    }

    @Operation(summary = "Создать новую покупку")
    @ApiResponse(responseCode = "201", description = "Покупка успешно создана")
    @PostMapping
    public Purchase createPurchase(@RequestBody Purchase purchase)
    {
        return purchaseService.create(purchase);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить покупку по ID")
    @ApiResponse(responseCode = "200", description = "Покупка успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Покупка не найдена")
    public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase)
    {
        return purchaseService.update(id, purchase);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить покупку по ID")
    @ApiResponse(responseCode = "204", description = "Покупка успешно удалена")
    @ApiResponse(responseCode = "404", description = "Покупка не найдена")
    public void deletePurchase(@PathVariable Long id)
    {
        purchaseService.delete(id);
    }

    @GetMapping("/best-employees/{positionId}")
    @Operation(summary = "Получить лучших сотрудников по позиции")
    @ApiResponse(responseCode = "200", description = "Список лучших сотрудников по позиции")
    @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    public ResponseEntity<List<EmployeeSalesDTO>> getBestEmployeesByPosition(@PathVariable Long positionId)
    {
        return ResponseEntity.ok(purchaseService.getBestEmployeesByPosition(positionId));
    }

    @GetMapping("/best-junior-smartwatch")
    @Operation(summary = "Получить лучшего младшего консультанта по покупке смарт-часов")
    @ApiResponse(responseCode = "200", description = "Лучший младший консультант по покупке смарт-часов найден")
    @ApiResponse(responseCode = "404", description = "Лучший младший консультант не найден")
    public ResponseEntity<EmployeeDTO> getBestJuniorSmartwatchEmployee()
    {
        EmployeeDTO result = purchaseService.getBestJuniorConsultantSmartWatch();
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/total-cash-payments")
    @Operation(summary = "Получить общую сумму наличных платежей")
    @ApiResponse(responseCode = "200", description = "Общая сумма наличных платежей успешно получена")
    @ApiResponse(responseCode = "500", description = "Ошибка при получении суммы наличных платежей")
    public ResponseEntity<BigDecimal> getTotalCashPayments()
    {
        return ResponseEntity.ok(purchaseService.getTotalCashPayments());
    }
}