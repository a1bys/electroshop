package com.test_task.backend.repository;

import com.test_task.backend.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>
{

}