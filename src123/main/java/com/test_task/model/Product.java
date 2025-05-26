package com.test_task.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private ProductType type;

    private BigDecimal price;
    private Integer quantity;
    private Boolean archived;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Product() {}

    // Получить id товара
    public Long getId()
    {
        return id;
    }
    // Установить id товара
    public void setId(Long id)
    {
        this.id = id;
    }

    // Получить название товара
    public String getName()
    {
        return name;
    }
    // Установить название товара
    public void setName(String name)
    {
        this.name = name;
    }

    // Получить тип товара
    public ProductType getType()
    {
        return type;
    }
    // Установить тип товара
    public void setType(ProductType type)
    {
        this.type = type;
    }

    // Получить цену товара
    public BigDecimal getPrice()
    {
        return price;
    }
    // Установить цену товара
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    // Получить количество товара
    public Integer getQuantity()
    {
        return quantity;
    }
    // Установить количество товара
    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    // Получить статус архивации товара
    public Boolean getArchived()
    {
        return archived;
    }
    // Установить статус архивации товара
    public void setArchived(Boolean archived)
    {
        this.archived = archived;
    }

    // Получить описание товара
    public String getDescription()
    {
        return description;
    }
    // Установить описание товара
    public void setDescription(String description)
    {
        this.description = description;
    }
}