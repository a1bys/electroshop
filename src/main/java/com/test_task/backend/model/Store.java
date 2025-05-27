package com.test_task.backend.model;

import jakarta.persistence.*;

@Entity
public class Store
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    public Store() {}

    // Получить id магазина
    public Long getId()
    {
        return id;
    }
    // Установить id магазина
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить название магазина
    public String getName()
    {
        return name;
    }
    // Установить название магазина
    public void setName(String name)
    {
        this.name = name;
    }

    // Получить адрес магазина
    public String getAddress()
    {
        return address;
    }
    // Установить адрес магазина
    public void setAddress(String address)
    {
        this.address = address;
    }
}