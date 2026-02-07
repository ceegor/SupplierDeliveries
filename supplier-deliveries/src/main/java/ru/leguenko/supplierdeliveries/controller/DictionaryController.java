package ru.leguenko.supplierdeliveries.controller;

import org.springframework.web.bind.annotation.*;
import ru.leguenko.supplierdeliveries.dto.ProductDto;
import ru.leguenko.supplierdeliveries.dto.SupplierDto;
import ru.leguenko.supplierdeliveries.service.DictionaryService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/suppliers")
    public List<SupplierDto> suppliers() {
        return dictionaryService.getSuppliers();
    }

    @GetMapping("/products")
    public List<ProductDto> products() {
        return dictionaryService.getProducts();
    }
}
