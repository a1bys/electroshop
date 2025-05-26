package com.test_task.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Purchase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Store store;

    private LocalDate timestamp;

    @ManyToOne
    private PurchaseType purchaseType;

    public Purchase() {}

    // Получить id покупки
    public Long getId()
    {
        return id;
    }
    // Установить id покупки
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить товар покупки
    public Product getProduct()
    {
        return product;
    }
    // Установить товар покупки
    public void setProduct(Product product)
    {
        this.product = product;
    }

    // Получить сотрудника, совершившего покупку
    public Employee getEmployee()
    {
        return employee;
    }
    // Установить сотрудника, совершившего покупку
    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    // Получить магазин, где совершена покупка
    public Store getStore()
    {
        return store;
    }
    // Установить магазин, где совершена покупка
    public void setStore(Store store)
    {
        this.store = store;
    }

    // Получить дату и время покупки
    public LocalDate getTimestamp()
    {
        return timestamp;
    }
    // Установить дату и время покупки
    public void setTimestamp(LocalDate timestamp)
    {
        this.timestamp = timestamp;
    }

    // Получить тип покупки
    public PurchaseType getPurchaseType()
    {
        return purchaseType;
    }
    // Установить тип покупки
    public void setPurchaseType(PurchaseType purchaseType)
    {
        this.purchaseType = purchaseType;
    }
}