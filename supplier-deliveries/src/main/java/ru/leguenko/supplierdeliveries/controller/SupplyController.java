package ru.leguenko.supplierdeliveries.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.leguenko.supplierdeliveries.dto.supply.CreateSupplyRequest;
import ru.leguenko.supplierdeliveries.dto.supply.SupplyResponse;
import ru.leguenko.supplierdeliveries.service.SupplyService;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    public SupplyResponse create(@Valid @RequestBody CreateSupplyRequest request) {
        return supplyService.create(request);
    }
}
