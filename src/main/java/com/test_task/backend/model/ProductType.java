package com.test_task.backend.model;

import jakarta.persistence.*;

@Entity
public class ProductType
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public ProductType() {}

    // Получить id типа товара
    public Long getId()
    {
        return id;
    }
    // Установить id типа товара
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить название типа товара
    public String getName()
    {
        return name;
    }
    // Установить название типа товара
    public void setName(String name)
    {
        this.name = name;
    }
}