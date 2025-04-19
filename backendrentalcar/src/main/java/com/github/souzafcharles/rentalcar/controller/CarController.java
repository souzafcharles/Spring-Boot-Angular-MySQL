package com.github.souzafcharles.rentalcar.controller;

import java.util.List;

import com.github.souzafcharles.rentalcar.entity.Car;
import com.github.souzafcharles.rentalcar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/cars")
@CrossOrigin("*")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Car>> findAll() {
        List<Car> list = this.carService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<Car>> findByName(@RequestParam("name") String name) {
        List<Car> list = this.carService.findByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Car> findById(@PathVariable("id") long id) {
        Car car = this.carService.findById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        String message = this.carService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Car car) {
        String message = this.carService.save(car);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Car car, @PathVariable("id") long id) {
        String message = this.carService.update(car, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}