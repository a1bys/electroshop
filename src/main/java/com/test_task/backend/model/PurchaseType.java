package com.test_task.backend.model;

import jakarta.persistence.*;

@Entity
public class PurchaseType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public PurchaseType() {}

    // Получить id типа покупки
    public Long getId()
    {
        return id;
    }
    // Установить id типа покупки
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить название типа покупки
    public String getName()
    {
        return name;
    }
    // Установить название типа покупки
    public void setName(String name)
    {
        this.name = name;
    }
}