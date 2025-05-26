package com.test_task.service;

import com.test_task.model.ProductType;
import com.test_task.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTypeService
{
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public ProductType create(ProductType productType)
    {
        return productTypeRepository.save(productType);
    }

    public Optional<ProductType> findById(Long id)
    {
        return productTypeRepository.findById(id);
    }

    public List<ProductType> findAll()
    {
        return productTypeRepository.findAll();
    }

    public ProductType update(Long id, ProductType updatedProductType)
    {
        return productTypeRepository.findById(id)
                .map(productType ->
                {
                    productType.setName(updatedProductType.getName());
                    return productTypeRepository.save(productType);
                })
                .orElseThrow(() -> new RuntimeException("Product type not found"));
    }

    public void delete(Long id)
    {
        productTypeRepository.deleteById(id);
    }

    public List<ProductType> getAllProductTypes()
    {
        return productTypeRepository.findAll();
    }

    public ProductType getProductTypeById(Long id)
    {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product type not found"));
    }

}