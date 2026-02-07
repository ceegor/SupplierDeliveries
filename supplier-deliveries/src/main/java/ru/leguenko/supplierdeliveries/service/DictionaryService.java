package ru.leguenko.supplierdeliveries.service;

import org.springframework.stereotype.Service;
import ru.leguenko.supplierdeliveries.dto.ProductDto;
import ru.leguenko.supplierdeliveries.dto.SupplierDto;
import ru.leguenko.supplierdeliveries.entity.Product;
import ru.leguenko.supplierdeliveries.entity.Supplier;
import ru.leguenko.supplierdeliveries.repository.ProductRepository;
import ru.leguenko.supplierdeliveries.repository.SupplierRepository;

import java.util.List;

@Service
public class DictionaryService {
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public DictionaryService(SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    public List<SupplierDto> getSuppliers() {
        return supplierRepository.findAll().stream().map(this::toDto).toList();
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    private SupplierDto toDto(Supplier supplier) {
        SupplierDto dto = new SupplierDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        return dto;
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
