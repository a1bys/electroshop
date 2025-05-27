package com.test_task.backend.controller;

import com.test_task.backend.model.Position;
import com.test_task.backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/positions")
@Tag(name = "Должности", description = "Управление позициями")
public class PositionController
{
    @Autowired
    private PositionService positionService;

    @Operation(summary = "Создать должность")
    @ApiResponse(responseCode = "200", description = "Позиция успешно создана")
    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position)
    {
        Position created = positionService.create(position);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Получить должность по ID")
    @ApiResponse(responseCode = "200", description = "Должность найдена")
    @ApiResponse(responseCode = "404", description = "Должность не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<Position> getById(@PathVariable Long id)
    {
        return positionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить все позиции")
    @ApiResponse(responseCode = "200", description = "Список должностей успешно получен")
    @ApiResponse(responseCode = "404", description = "Должности не найдены")
    @GetMapping
    public List<Position> getAll()
    {
        return positionService.findAll();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить должность по ID")
    @ApiResponse(responseCode = "200", description = "Должность успешно обновлена")
    @ApiResponse(responseCode = "404", description = "Должность не найдена")
    public ResponseEntity<Position> update(@PathVariable Long id, @RequestBody Position position)
    {
        try
        {
            Position updated = positionService.update(id, position);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить должность по ID")
    @ApiResponse(responseCode = "204", description = "Должность успешно удалена")
    @ApiResponse(responseCode = "404", description = "Должность не найдена")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}