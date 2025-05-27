package com.test_task.backend.service;

import com.test_task.backend.model.*;
import com.test_task.backend.repository.*;
import org.apache.commons.csv.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class CSVImportService
{
    private final PositionRepository positionRepository;
    private final StoreRepository storeRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;

    public CSVImportService(PositionRepository positionRepository, StoreRepository storeRepository,
                            ProductTypeRepository productTypeRepository, ProductRepository productRepository,
                            EmployeeRepository employeeRepository, PurchaseRepository purchaseRepository, PurchaseTypeRepository purchaseTypeRepository)
    {
        this.positionRepository = positionRepository;
        this.storeRepository = storeRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
    }

    public int importFromZip(ZipInputStream zis) throws Exception
    {
        ZipEntry entry;
        int totalImported = 0;

        while ((entry = zis.getNextEntry()) != null)
        {
            String name = entry.getName().toLowerCase();
            BufferedReader reader = new BufferedReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            switch(name)
            {
                case "position.csv":
                    for (CSVRecord record : parser)
                    {
                        Position position = new Position();
                        position.setId(Long.parseLong(record.get("id")));
                        position.setName(record.get("name"));
                        positionRepository.save(position);
                        totalImported++;
                    }
                    break;
                case "store.csv":
                    for (CSVRecord record : parser)
                    {
                        Store store = new Store();
                        store.setId(Long.parseLong(record.get("id")));
                        store.setName(record.get("name"));
                        store.setAddress(record.get("address"));
                        storeRepository.save(store);
                        totalImported++;
                    }
                    break;
                case "product_types.csv":
                    for (CSVRecord record : parser)
                    {
                        ProductType productType = new ProductType();
                        productType.setId(Long.parseLong(record.get("id")));
                        productType.setName(record.get("name"));
                        productTypeRepository.save(productType);
                        totalImported++;
                    }
                    break;
                case "products.csv":
                    for (CSVRecord record : parser)
                    {
                        Product product = new Product();
                        product.setName(record.get("name"));
                        product.setType(productTypeRepository.findById(Long.parseLong(record.get("type_id"))).orElseThrow());
                        product.setPrice(new java.math.BigDecimal(record.get("price")));
                        product.setQuantity(Integer.parseInt(record.get("quantity")));
                        product.setArchived(Boolean.parseBoolean(record.get("archived")));
                        product.setDescription(record.get("description"));
                        productRepository.save(product);
                        totalImported++;
                    }
                    break;
                case "employee.csv":
                    for (CSVRecord record : parser)
                    {
                        Employee employee = new Employee();
                        employee.setId(Long.parseLong(record.get("id")));
                        employee.setLastName(record.get("last_name"));
                        employee.setFirstName(record.get("first_name"));
                        employee.setMiddleName(record.get("middle_name"));
                        employee.setDateOfBirth(LocalDate.parse(record.get("date_of_birth")));
                        employee.setGender(Boolean.parseBoolean(record.get("gender")));
                        employee.setPosition(positionRepository.findById(Long.parseLong(record.get("position_id"))).orElseThrow());
                        employee.setStore(storeRepository.findById(Long.parseLong(record.get("store_id"))).orElseThrow());
                        employeeRepository.save(employee);
                        totalImported++;
                    }
                    break;
                case "purchase.csv":
                    for (CSVRecord record : parser)
                    {
                        Product product = productRepository.findById(Long.parseLong(record.get("product_id"))).orElseThrow();
                        if (product.getQuantity() <= 0) continue;

                        Employee employee = employeeRepository.findById(Long.parseLong(record.get("employee_id"))).orElseThrow();
                        Store store = storeRepository.findById(Long.parseLong(record.get("store_id"))).orElseThrow();
                        PurchaseType purchaseType = purchaseTypeRepository.findById(Long.parseLong(record.get("purchase_type_id"))).orElseThrow();

                        Purchase purchase = new Purchase();
                        purchase.setId(Long.parseLong(record.get("purchase_id")));
                        purchase.setProduct(product);
                        purchase.setEmployee(employee);
                        purchase.setStore(store);
                        purchase.setTimestamp(LocalDate.parse(record.get("timestamp")));
                        purchase.setPurchaseType(purchaseType);
                        purchaseRepository.save(purchase);
                        product.setQuantity(product.getQuantity() - 1); // Уменьшаем количество товара на 1
                        productRepository.save(product); // Сохраняем обновленный товар
                        totalImported++;
                    }
                    break;
                case "purchase_type.csv":
                    for (CSVRecord record : parser)
                    {
                        PurchaseType purchaseType = new PurchaseType();
                        purchaseType.setId(Long.parseLong(record.get("id")));
                        purchaseType.setName(record.get("name"));
                        purchaseTypeRepository.save(purchaseType);
                        totalImported++;
                    }
                    break;
            }
            parser.close();
        }
        return totalImported;
    }
}