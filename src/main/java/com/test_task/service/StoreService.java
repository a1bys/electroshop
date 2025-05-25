package com.test_task.service;

import com.test_task.repository.StoreRepository;
import com.test_task.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService
{
    @Autowired
    private StoreRepository storeRepository;

    public Store create(Store store)
    {
        return storeRepository.save(store);
    }

    public Optional<Store> getById(Long id)
    {
        return storeRepository.findById(id);
    }

    public List<Store> getAll()
    {
        return storeRepository.findAll();
    }

    public Store update(Long id, Store store)
    {
        return storeRepository.findById(id)
                .map(existingStore ->
                {
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

    public List<Store> getAllStores()
    {
        return storeRepository.findAll();
    }

    public Store getStoreById(Long id)
    {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found"));
    }
}