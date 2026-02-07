package ru.leguenko.supplierdeliveries.dto.supply;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SupplyLineResponse {
    private Long productId;
    private BigDecimal weightKg;
    private BigDecimal pricePerKg;
    private BigDecimal amount;
}
