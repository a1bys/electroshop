package com.test_task.backend.service;

import com.test_task.backend.repository.StoreRepository;
import com.test_task.backend.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService
{
    @Autowired
    private StoreRepository storeRepository;

    public Store create(Store store)
    {
        return storeRepository.save(store);
    }

    public Store getById(Long id)
    {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    public List<Store> getAll()
    {
        return storeRepository.findAll();
    }

    public Store update(Long id, Store store)
    {
        return storeRepository.findById(id)
                .map(existingStore -> {
                    existingStore.setName(store.getName());
                    existingStore.setAddress(store.getAddress());
                    return storeRepository.save(existingStore);
                })
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }

    public void delete(Long id)
    {
        storeRepository.deleteById(id);
    }
}