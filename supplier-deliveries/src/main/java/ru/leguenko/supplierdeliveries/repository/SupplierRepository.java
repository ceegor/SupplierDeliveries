package ru.leguenko.supplierdeliveries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leguenko.supplierdeliveries.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {}
