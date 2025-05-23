package com.test_task.controller;

import com.test_task.model.Position;
import com.test_task.service.PositionService;
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
    public ResponseEntity<Position> getById(@PathVariable Integer id)
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
    public ResponseEntity<Position> update(@PathVariable Integer id, @RequestBody Position position)
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
    public ResponseEntity<Void> delete(@PathVariable Integer id)
    {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}