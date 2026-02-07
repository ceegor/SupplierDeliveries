package ru.leguenko.supplierdeliveries.dto.supply;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateSupplyLineRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private BigDecimal weightKg;
}
