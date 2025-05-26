package com.test_task.model;

import jakarta.persistence.*;

@Entity
public class Position
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Position() {}

    // Получить id должности
    public Long getId()
    {
        return id;
    }
    // Установить id должности
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить название должности
    public String getName()
    {
        return name;
    }
    // Установить название должности
    public void setName(String name)
    {
        this.name = name;
    }
}