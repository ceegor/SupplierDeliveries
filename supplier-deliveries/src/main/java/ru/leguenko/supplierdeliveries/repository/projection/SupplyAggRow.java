package ru.leguenko.supplierdeliveries.repository.projection;

import java.math.BigDecimal;

public interface SupplyAggRow {
    Long getSupplierId();
    String getSupplierName();

    Long getProductId();
    String getProductCode();
    String getProductName();
    String getProductCategory();

    BigDecimal getTotalWeightKg();
    BigDecimal getTotalAmount();
}
