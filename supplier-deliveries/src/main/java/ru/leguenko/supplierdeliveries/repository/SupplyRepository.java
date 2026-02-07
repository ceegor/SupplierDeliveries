package ru.leguenko.supplierdeliveries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leguenko.supplierdeliveries.entity.Supply;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}
