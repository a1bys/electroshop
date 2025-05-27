package com.test_task.backend.controller;

import com.test_task.backend.model.ProductType;
import com.test_task.backend.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
@Tag(name = "Типы продуктов", description = "API для управления типами продуктов")
public class ProductTypeController
{
    private final ProductTypeService productTypeService;

    @Autowired
    public ProductTypeController(ProductTypeService productTypeService)
    {
        this.productTypeService = productTypeService;
    }

    @Operation(summary = "Получить все типы продуктов")
    @ApiResponse(responseCode = "200", description = "Список типов продуктов успешно получен")
    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProductTypes()
    {
        List<ProductType> productTypes = productTypeService.getAllProductTypes();
        return ResponseEntity.ok(productTypes);
    }

    @Operation(summary = "Получить тип продукта по ID")
    @ApiResponse(responseCode = "200", description = "Тип продукта успешно получен")
    @ApiResponse(responseCode = "404", description = "Тип продукта не найден")
    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getProductTypeById(@PathVariable Long id)
    {
        ProductType productType = productTypeService.getProductTypeById(id);
        return ResponseEntity.ok(productType);
    }

    @Operation(summary = "Создать новый тип продукта")
    @ApiResponse(responseCode = "201", description = "Тип продукта успешно создан")
    @PostMapping
    public ResponseEntity<ProductType> createProductType(@RequestBody ProductType productType)
    {
        ProductType createdProductType = productTypeService.create(productType);
        return ResponseEntity.status(201).body(createdProductType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип продукта по ID")
    @ApiResponse(responseCode = "200", description = "Тип продукта успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Тип продукта не найден")
    public ResponseEntity<ProductType> updateProductType(@PathVariable Long id, @RequestBody ProductType productType)
    {
        ProductType updatedProductType = productTypeService.update(id, productType);
        return ResponseEntity.ok(updatedProductType);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип продукта по ID")
    @ApiResponse(responseCode = "204", description = "Тип продукта успешно удален")
    @ApiResponse(responseCode = "404", description = "Тип продукта не найден")
    public ResponseEntity<Void> deleteProductType(@PathVariable Long id)
    {
        productTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}