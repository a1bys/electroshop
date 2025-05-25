package com.test_task.service;

import com.test_task.model.Position;
import com.test_task.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService
{
    @Autowired
    private PositionRepository positionRepository;

    public Position create(Position position)
    {
        return positionRepository.save(position);
    }

    public Optional<Position> findById(Long id)
    {
        return positionRepository.findById(id);
    }

    public List<Position> findAll()
    {
        return positionRepository.findAll();
    }

    public Position update(Long id, Position updatedPosition)
    {
        return positionRepository.findById(id)
                .map(position ->
                {
                    position.setName(updatedPosition.getName());
                    return positionRepository.save(position);
                })
                .orElseThrow(() -> new RuntimeException("Position not found"));
    }

    public void delete(Long id)
    {
        positionRepository.deleteById(id);
    }
}

