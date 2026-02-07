package ru.leguenko.supplierdeliveries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.leguenko.supplierdeliveries.entity.SupplierPrice;

import java.time.LocalDate;
import java.util.Optional;

public interface SupplierPriceRepository extends JpaRepository<SupplierPrice, Long> {

    @Query("""
           select sp from SupplierPrice sp
           where sp.supplier.id = :supplierId
           and sp.product.id = :productId
           and :date between sp.validFrom and sp.validTo
    """)
    Optional<SupplierPrice> findPriceForDate(
            @Param("supplierId") Long supplierId,
            @Param("productId") Long productId,
            @Param("date") LocalDate date
    );
}
