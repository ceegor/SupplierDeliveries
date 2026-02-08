package ru.leguenko.supplierdeliveries.service;

import org.springframework.stereotype.Service;
import ru.leguenko.supplierdeliveries.dto.report.ReportProductRowDto;
import ru.leguenko.supplierdeliveries.dto.report.ReportSupplierBlockDto;
import ru.leguenko.supplierdeliveries.dto.report.SuppliesReportResponse;
import ru.leguenko.supplierdeliveries.entity.ProductCategory;
import ru.leguenko.supplierdeliveries.repository.SupplyRepository;
import ru.leguenko.supplierdeliveries.repository.projection.SupplyAggRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final SupplyRepository supplyRepository;

    public ReportService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    public SuppliesReportResponse supplies(LocalDate from, LocalDate to) {
        LocalDateTime fromDt = from.atStartOfDay();
        LocalDateTime toExclusive = to.plusDays(1).atStartOfDay();

        List<SupplyAggRow> rows = supplyRepository.aggregateBySupplierAndProduct(fromDt, toExclusive);

        Map<Long, ReportSupplierBlockDto> supplierMap = new LinkedHashMap<>();

        BigDecimal grandWeight = BigDecimal.ZERO;
        BigDecimal grandAmount = BigDecimal.ZERO;

        for (SupplyAggRow r : rows) {
            ReportSupplierBlockDto sup = supplierMap.computeIfAbsent(r.getSupplierId(), id -> {
                ReportSupplierBlockDto dto = new ReportSupplierBlockDto();
                dto.setSupplierId(r.getSupplierId());
                dto.setSupplierName(r.getSupplierName());
                dto.setProducts(new ArrayList<>());
                dto.setTotalWeightKg(BigDecimal.ZERO);
                dto.setTotalAmount(BigDecimal.ZERO);
                return dto;
            });

            BigDecimal w = nz(r.getTotalWeightKg());
            BigDecimal a = nz(r.getTotalAmount());

            ReportProductRowDto pr = new ReportProductRowDto();
            pr.setProductId(r.getProductId());
            pr.setProductCode(r.getProductCode());
            pr.setProductName(r.getProductName());
            pr.setProductCategory(ProductCategory.valueOf(r.getProductCategory()));
            pr.setTotalWeightKg(w.setScale(3, RoundingMode.HALF_UP));
            pr.setTotalAmount(a.setScale(2, RoundingMode.HALF_UP));

            sup.getProducts().add(pr);

            sup.setTotalWeightKg(nz(sup.getTotalWeightKg()).add(w));
            sup.setTotalAmount(nz(sup.getTotalAmount()).add(a));

            grandWeight = grandWeight.add(w);
            grandAmount = grandAmount.add(a);
        }

        for (ReportSupplierBlockDto s : supplierMap.values()) {
            s.setTotalWeightKg(nz(s.getTotalWeightKg()).setScale(3, RoundingMode.HALF_UP));
            s.setTotalAmount(nz(s.getTotalAmount()).setScale(2, RoundingMode.HALF_UP));
        }

        SuppliesReportResponse resp = new SuppliesReportResponse();
        resp.setFrom(from);
        resp.setTo(to);
        resp.setSuppliers(new ArrayList<>(supplierMap.values()));
        resp.setTotalWeightKg(grandWeight.setScale(3, RoundingMode.HALF_UP));
        resp.setTotalAmount(grandAmount.setScale(2, RoundingMode.HALF_UP));
        return resp;
    }

    private BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
