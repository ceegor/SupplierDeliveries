package ru.leguenko.supplierdeliveries.dto;

import lombok.*;
import ru.leguenko.supplierdeliveries.entity.ProductCategory;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String code;
    private String name;
    private ProductCategory category;
}
