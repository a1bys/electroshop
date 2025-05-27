package com.test_task.backend.repository;

import com.test_task.backend.model.PurchaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Long>
{

}