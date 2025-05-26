package com.test_task.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate dateOfBirth;
    private Boolean gender; //true муж.,false жен.

    @ManyToOne
    private Position position;

    @ManyToOne
    private Store store;

    public Employee()
    {}

    //получить id
    public Long getId()
    {
        return id;
    }
    //установить id
    public void setId(Long id)
    {
        this.id = id;
    }

    //получить Фамилию
    public String getLastName()
    {
        return lastName;
    }
    //установить Фамилию
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    //получить Имя
    public String getFirstName()
    {
        return firstName;
    }
    //установить Имя
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    //получить Отчество
    public String getMiddleName()
    {
        return middleName;
    }
    //установить Отчество
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    //получить Дату рождения
    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }
    //установить Дату рождения
    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    //получить Пол
    public Boolean getGender()
    {
        return gender;
    }
    //установить Пол
    public void setGender(Boolean gender)
    {
        this.gender = gender;
    }

    //получить Должность
    public Position getPosition()
    {
        return position;
    }
    //установить Должность
    public void setPosition(Position position)
    {
        this.position = position;
    }

    //получить Магазин
    public Store getStore()
    {
        return store;
    }
    //установить Магазин
    public void setStore(Store store)
    {
        this.store = store;
    }
}