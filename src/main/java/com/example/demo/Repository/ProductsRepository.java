package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Product;

public interface ProductsRepository extends JpaRepository<Product , Integer> {

	List<Product> findByNameContainingIgnoreCase(String keyword);

}
