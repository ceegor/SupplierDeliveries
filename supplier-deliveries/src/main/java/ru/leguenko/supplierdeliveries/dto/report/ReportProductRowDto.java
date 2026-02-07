package ru.leguenko.supplierdeliveries.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReportProductRowDto {
    private Long productId;
    private String productCode;
    private String productName;
    private String productCategory;

    private BigDecimal totalWeightKg;
    private BigDecimal totalAmount;
}
