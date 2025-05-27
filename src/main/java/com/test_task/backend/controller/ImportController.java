package com.test_task.backend.controller;

import com.test_task.backend.service.CSVImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/api/import")
public class ImportController
{
    private final CSVImportService csvImportService;

    public ImportController(CSVImportService csvImportService)
    {
        this.csvImportService = csvImportService;
    }

    @PostMapping
    public ResponseEntity<String> importFromZIP(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty())
        {
            return ResponseEntity.badRequest().body("Файл пуст");
        }

        try (ZipInputStream zis = new ZipInputStream(file.getInputStream()))
        {
            int importedCount = csvImportService.importFromZip(zis);
            return ResponseEntity.ok("Импортировано записей: " + importedCount);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Ошибка при импорте: " + e.getMessage());
        }
    }
}