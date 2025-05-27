package com.test_task.backend.controller;

import com.test_task.backend.model.Position;
import com.test_task.backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
public class PositionController
{
    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody Position position)
    {
        Position created = positionService.create(position);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> getById(@PathVariable Long id)
    {
        return positionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Position> getAll()
    {
        return positionService.findAll();
    }

    @PutMapping("/{id}")
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
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}