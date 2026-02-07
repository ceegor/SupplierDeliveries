package ru.leguenko.supplierdeliveries.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leguenko.supplierdeliveries.dto.supply.CreateSupplyLineRequest;
import ru.leguenko.supplierdeliveries.dto.supply.CreateSupplyRequest;
import ru.leguenko.supplierdeliveries.dto.supply.SupplyLineResponse;
import ru.leguenko.supplierdeliveries.dto.supply.SupplyResponse;
import ru.leguenko.supplierdeliveries.entity.*;
import ru.leguenko.supplierdeliveries.exception.BusinessException;
import ru.leguenko.supplierdeliveries.exception.NotFoundException;
import ru.leguenko.supplierdeliveries.repository.ProductRepository;
import ru.leguenko.supplierdeliveries.repository.SupplierPriceRepository;
import ru.leguenko.supplierdeliveries.repository.SupplierRepository;
import ru.leguenko.supplierdeliveries.repository.SupplyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplierRepository supplierRepository;
    private final SupplyRepository supplyRepository;
    private final SupplierPriceRepository supplierPriceRepository;
    private final ProductRepository productRepository;

    @Transactional
    public SupplyResponse create(CreateSupplyRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found" + request.getSupplierId()));

        Supply supply = new Supply();
        supply.setSupplier(supplier);
        supply.setSupplyDate(request.getSupplyDate());

        LocalDate date = request.getSupplyDate().toLocalDate();

        for (CreateSupplyLineRequest lineRequest : request.getLines()) {
            Product product = productRepository.findById(lineRequest.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found" + lineRequest.getProductId()));

            SupplierPrice price = supplierPriceRepository.findPriceForDate(
                    supplier.getId(),
                    product.getId(),
                    date
            ).orElseThrow(() -> new BusinessException(
                    "NO_PRICE",
                    "No price for supplierId=" + supplier.getId() +
                            ", productId=" + product.getId() +
                            " on date=" + date
            ));

            SupplyLine line = new SupplyLine();
            line.setProduct(product);
            line.setWeightKg(lineRequest.getWeightKg());
            line.setPricePerKg(price.getPricePerKg());

            supply.addLine(line);
        }

        Supply saved = supplyRepository.save(supply);
        return toResponse(saved);
    }

    private SupplyResponse toResponse(Supply supply) {
        SupplyResponse response = new SupplyResponse();
        response.setId(supply.getId());
        response.setSupplierId(supply.getSupplier().getId());
        response.setSupplyDate(supply.getSupplyDate());

        List<SupplyLineResponse> lineResponses = new ArrayList<>();

        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (SupplyLine line : supply.getLines()) {
            BigDecimal amount = line.getWeightKg()
                    .multiply(line.getPricePerKg())
                    .setScale(2, RoundingMode.HALF_UP);

            SupplyLineResponse lr = new SupplyLineResponse();
            lr.setProductId(line.getProduct().getId());
            lr.setWeightKg(line.getWeightKg());
            lr.setPricePerKg(line.getPricePerKg());
            lr.setAmount(amount);

            lineResponses.add(lr);

            totalWeight = totalWeight.add(line.getWeightKg());
            totalAmount = totalAmount.add(amount);
        }

        response.setLines(lineResponses);
        response.setTotalWeightKg(totalWeight.setScale(3, RoundingMode.HALF_UP));
        response.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
        return response;
    }
}
