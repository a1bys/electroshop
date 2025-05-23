package com.test_task.service;

import com.test_task.model.Purchase;
import com.test_task.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService
{
    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase create(Purchase purchase)
    {
        return purchaseRepository.save(purchase);
    }

    public Optional<Purchase> findById(Long id)
    {
        return purchaseRepository.findById(id);
    }

    public List<Purchase> findAll()
    {
        return purchaseRepository.findAll();
    }

    public Purchase update(Long id, Purchase updatedPurchase)
    {
        return purchaseRepository.findById(id)
                .map(purchase ->
                {
                    purchase.setProduct(updatedPurchase.getProduct());
                    purchase.setEmployee(updatedPurchase.getEmployee());
                    purchase.setStore(updatedPurchase.getStore());
                    purchase.setTimestamp(updatedPurchase.getTimestamp());
                    purchase.setPurchaseType(updatedPurchase.getPurchaseType());
                    return purchaseRepository.save(purchase);
                })
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public void delete(Long id)
    {
        purchaseRepository.deleteById(id);
    }

    public List<Purchase> getAllPurchases()
    {
        return purchaseRepository.findAll();
    }

    public Purchase getPurchaseById(Long id)
    {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }

    public Purchase createPurchase(Purchase purchase)
    {
        return purchaseRepository.save(purchase);
    }

    public Purchase updatePurchase(Long id, Purchase purchase)
    {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        existingPurchase.setProduct(purchase.getProduct());
        existingPurchase.setEmployee(purchase.getEmployee());
        existingPurchase.setStore(purchase.getStore());
        existingPurchase.setTimestamp(purchase.getTimestamp());
        existingPurchase.setPurchaseType(purchase.getPurchaseType());
        return purchaseRepository.save(existingPurchase);
    }

    public void deletePurchase(Long id)
    {
        purchaseRepository.deleteById(id);
    }
}