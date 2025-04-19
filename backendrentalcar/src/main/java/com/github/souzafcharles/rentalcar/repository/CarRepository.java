package com.github.souzafcharles.rentalcar.repository;

import com.github.souzafcharles.rentalcar.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findByNameContainingIgnoreCase(String name);
}