package com.github.souzafcharles.rentalcar.repository;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    public List<Accessory> findByNameContainingIgnoreCase(String name);
}