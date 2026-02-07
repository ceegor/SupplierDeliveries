package ru.leguenko.supplierdeliveries.dto.supply;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SupplyResponse {
    private Long id;
    private Long supplierId;
    private LocalDateTime supplyDate;

    private BigDecimal totalWeightKg;
    private BigDecimal totalAmount;

    private List<SupplyLineResponse> lines;
}
