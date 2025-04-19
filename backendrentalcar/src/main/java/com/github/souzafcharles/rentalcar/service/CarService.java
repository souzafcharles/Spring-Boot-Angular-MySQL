package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Car;
import com.github.souzafcharles.rentalcar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAll() {
        return this.carRepository.findAll();
    }

    public Car findById(long id) {
        Optional<Car> car = this.carRepository.findById(id);
        return car.orElse(null);
    }

    public List<Car> findByName(String name) {
        return this.carRepository.findByNameContainingIgnoreCase(name);
    }

    public String save(Car car) {
        this.carRepository.save(car);
        return "Car saved successfully.";
    }

    public String update(Car car, long id) {
        car.setId(id);
        this.carRepository.save(car);
        return "Car updated successfully.";
    }

    public String deleteById(long id) {
        this.carRepository.deleteById(id);
        return "Car deleted successfully.";
    }

}