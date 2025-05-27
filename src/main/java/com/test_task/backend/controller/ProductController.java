package com.test_task.backend.controller;

import com.test_task.backend.model.Product;
import com.test_task.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Продукты", description = "API для управления продуктами")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @Operation(summary = "Создать новый продукт")
    @ApiResponse(responseCode = "200", description = "Продукт успешно создан")
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product)
    {
        Product created = productService.create(product);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Получить продукт по ID")
    @ApiResponse(responseCode = "200", description = "Продукт найден")
    @ApiResponse(responseCode = "404", description = "Продукт не найден")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id)
    {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить все продукты")
    @ApiResponse(responseCode = "200", description = "Список продуктов успешно получен")
    @GetMapping
    public List<Product> getAll()
    {
        return productService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить продукт по ID")
    @ApiResponse(responseCode = "200", description = "Продукт успешно обновлен")
    @ApiResponse(responseCode = "404", description = "Продукт не найден")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product)
    {
        try
        {
            Product updated = productService.update(id, product);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить продукт по ID")
    @ApiResponse(responseCode = "204", description = "Продукт успешно удален")
    @ApiResponse(responseCode = "404", description = "Продукт не найден")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}