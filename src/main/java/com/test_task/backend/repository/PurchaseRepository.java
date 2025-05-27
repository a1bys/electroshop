package com.test_task.backend.repository;

import com.test_task.backend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>
{
    @Query("SELECT p.employee.id, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :fromDate GROUP BY p.employee.id")
    List<Object[]> findEmployeeSalesStatsSince(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT p.employee.id, COUNT(p) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :fromDate AND pr.type.name = :typeName GROUP BY p.employee.id")
    List<Object[]> findEmployeeSmartwatchSalesStatsSince(@Param("fromDate") LocalDateTime fromDate, @Param("typeName") String typeName);

    @Query("SELECT SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.store.id = :storeId AND p.purchaseType.name = :purchaseTypeName")
    Double findCashSalesSumByStore(@Param("storeId") Long storeId, @Param("purchaseTypeName") String purchaseTypeName);
}