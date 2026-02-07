package ru.leguenko.supplierdeliveries.dto.supply;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateSupplyRequest {

    @NotNull
    private Long supplierId;

    @NotNull
    private LocalDateTime supplyDate;

    @Valid
    @NotEmpty
    private List<CreateSupplyLineRequest> lines;
}
