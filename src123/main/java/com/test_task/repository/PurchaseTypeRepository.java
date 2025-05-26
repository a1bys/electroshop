package com.test_task.repository;

import com.test_task.model.PurchaseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Long>
{

}