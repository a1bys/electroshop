package com.test_task.backend.repository;

import com.test_task.backend.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long>
{

}