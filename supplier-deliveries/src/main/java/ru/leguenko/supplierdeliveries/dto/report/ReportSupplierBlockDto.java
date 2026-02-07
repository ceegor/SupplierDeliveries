package ru.leguenko.supplierdeliveries.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ReportSupplierBlockDto {
    private Long supplierId;
    private String supplierName;

    private BigDecimal totalWeightKg;
    private BigDecimal totalAmount;

    private List<ReportProductRowDto> products;
}
