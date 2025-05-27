package com.test_task.backend.service;

import com.test_task.backend.model.Product;
import com.test_task.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product)
    {
        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id)
    {
        return productRepository.findById(id);
    }

    public List<Product> findAll()
    {
        return productRepository.findAll();
    }

    public Product update(Long id, Product updatedProduct)
    {
        return productRepository.findById(id)
                .map(product ->
                {
                    product.setName(updatedProduct.getName());
                    product.setType(updatedProduct.getType());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setArchived(updatedProduct.getArchived());
                    product.setDescription(updatedProduct.getDescription());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(Long id)
    {
        productRepository.deleteById(id);
    }
}