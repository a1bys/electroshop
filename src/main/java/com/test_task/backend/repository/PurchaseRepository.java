package com.test_task.backend.repository;

import com.test_task.backend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>
{
    @Query("SELECT p.employee, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :yearAgo AND p.employee.position.id = :positionId GROUP BY p.employee ORDER BY COUNT(p) DESC")
    List<Object[]> findBestEmployeesByPositionAndQuantity(@Param("positionId") Long positionId, @Param("yearAgo") LocalDate yearAgo);

    @Query("SELECT p.employee, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :yearAgo AND p.employee.position.id = :positionId GROUP BY p.employee ORDER BY SUM(pr.price) DESC")
    List<Object[]> findBestEmployeesByPositionAndAmount(@Param("positionId") Long positionId, @Param("yearAgo") LocalDate yearAgo);

    @Query("SELECT p.employee, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE pr.type.name = 'Умные часы' AND p.employee.position.name = 'Младший продавец-консультант' GROUP BY p.employee ORDER BY COUNT(p) DESC LIMIT 1")
    Object[] findBestJuniorConsultantSmartWatchSales();

    @Query("SELECT SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.purchaseType.name = 'Наличные'")
    BigDecimal findTotalCashPayments();

    @Query("SELECT p.employee.id, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :fromDate AND pr.type.name = 'Умные часы' AND p.employee.position.name = 'Младший продавец-консультант' GROUP BY p.employee.id")
    List<Object[]> findBestJuniorSmartwatchSalesLastYear(@Param("fromDate") LocalDate fromDate);

    @Query("SELECT p.employee.id, COUNT(p), SUM(pr.price) FROM Purchase p JOIN p.product pr WHERE p.timestamp >= :fromDate GROUP BY p.employee.id")
    List<Object[]> findAllSalesByQuantity(@Param("fromDate") LocalDate fromDate);
}