package ru.leguenko.supplierdeliveries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leguenko.supplierdeliveries.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
