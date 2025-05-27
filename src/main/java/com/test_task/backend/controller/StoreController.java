package com.test_task.backend.controller;

import com.test_task.backend.model.Store;
import com.test_task.backend.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "Магазины", description = "Управление магазинами")
public class StoreController
{
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService)
    {
        this.storeService = storeService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех магазинов")
    @ApiResponse(responseCode = "200", description = "Список магазинов успешно получен")
    public List<Store> getAllStores()
    {
        return storeService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить магазин по ID")
    @ApiResponse(responseCode = "200", description = "Магазин успешно найден")
    @ApiResponse(responseCode = "404", description = "Магазин не найден")
    public Store getStoreById(@PathVariable Long id)
    {
        return storeService.getById(id);
    }

    @Operation(summary = "Создать новый магазин")
    @ApiResponse(responseCode = "201", description = "Магазин успешно создан")
    @PostMapping
    public Store createStore(@RequestBody Store store)
    {
        return storeService.create(store);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить магазин по ID")
    @ApiResponse(responseCode = "200", description = "Магазин успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Магазин не найден")
    public Store updateStore(@PathVariable Long id, @RequestBody Store store)
    {
        return storeService.update(id, store);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить магазин по ID")
    @ApiResponse(responseCode = "204", description = "Магазин успешно удален")
    @ApiResponse(responseCode = "404", description = "Магазин не найден")
    public void deleteStore(@PathVariable Long id)
    {
        storeService.delete(id);
    }
}