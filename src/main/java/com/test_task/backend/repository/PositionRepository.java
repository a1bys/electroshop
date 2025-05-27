package com.test_task.backend.repository;

import com.test_task.backend.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long>
{

}