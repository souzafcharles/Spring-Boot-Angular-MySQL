package com.github.souzafcharles.rentalcar.repository;

import com.github.souzafcharles.rentalcar.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    public List<Brand> findByNameContainingIgnoreCase(String name);
}