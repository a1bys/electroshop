package com.test_task.service;

import com.test_task.repository.PurchaseTypeRepository;
import com.test_task.model.PurchaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseTypeService
{
    @Autowired
    private PurchaseTypeRepository purchaseTypeRepository;

    public PurchaseType create(PurchaseType purchaseType)
    {
        return purchaseTypeRepository.save(purchaseType);
    }

    public Optional<PurchaseType> getById(Long id)
    {
        return purchaseTypeRepository.findById(id);
    }

    public List<PurchaseType> getAll()
    {
        return purchaseTypeRepository.findAll();
    }

    public PurchaseType update(Long id, PurchaseType updatedPurchaseType)
    {
        return purchaseTypeRepository.findById(id)
                .map(purchaseType ->
                {
                    purchaseType.setName(updatedPurchaseType.getName());
                    return purchaseTypeRepository.save(purchaseType);
                })
                .orElseThrow(() -> new RuntimeException("Purchase type not found"));
    }

    public void delete(Long id)
    {
        purchaseTypeRepository.deleteById(id);
    }

    public List<PurchaseType> getAllPurchaseTypes()
    {
        return purchaseTypeRepository.findAll();
    }

    public PurchaseType getPurchaseTypeById(Long id)
    {
        return purchaseTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase type not found"));
    }
}