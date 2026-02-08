package ru.leguenko.supplierdeliveries.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.leguenko.supplierdeliveries.entity.Supply;
import ru.leguenko.supplierdeliveries.repository.projection.SupplyAggRow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    @Query("""
    select
      s.supplier.id as supplierId,
      s.supplier.name as supplierName,
      p.id as productId,
      p.code as productCode,
      p.name as productName,
      cast(p.category as string) as productCategory,
      sum(l.weightKg) as totalWeightKg,
      sum(l.weightKg * l.pricePerKg) as totalAmount
    from Supply s
      join s.lines l
      join l.product p
    where s.supplyDate >= :from and s.supplyDate < :toExclusive
    group by s.supplier.id, s.supplier.name, p.id, p.code, p.name, p.category
    order by s.supplier.name, p.code
  """)
    List<SupplyAggRow> aggregateBySupplierAndProduct(
            @Param("from") LocalDateTime from,
            @Param("toExclusive") LocalDateTime toExclusive
    );

    @EntityGraph(attributePaths = {"supplier", "lines", "lines.product"})
    Optional<Supply> findWithDetailsById(Long id);
}
