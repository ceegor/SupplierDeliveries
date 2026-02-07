package ru.leguenko.supplierdeliveries.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SuppliesReportResponse {
    private LocalDate from;
    private LocalDate to;

    private BigDecimal totalWeightKg;
    private BigDecimal totalAmount;

    private List<ReportSupplierBlockDto> suppliers;
}
