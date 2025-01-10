package com.inventory.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can define custom queries here if needed
	Optional<Product> findByName(String name);
	
	 List<Product> findByStockQuantityLessThan(int quantity);
}
