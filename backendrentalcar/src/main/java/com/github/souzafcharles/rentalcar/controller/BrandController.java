package com.github.souzafcharles.rentalcar.controller;

import java.util.List;

import com.github.souzafcharles.rentalcar.entity.Brand;
import com.github.souzafcharles.rentalcar.service.BrandService;
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
@RequestMapping("/api/brands")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Brand>> findAll() {
        List<Brand> list = this.brandService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<Brand>> findByName(@RequestParam("name") String name) {
        List<Brand> list = this.brandService.findByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Brand> findById(@PathVariable("id") long id) {
        Brand brand = this.brandService.findById(id);
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        String message = this.brandService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Brand brand) {
        String message = this.brandService.save(brand);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Brand brand, @PathVariable("id") long id) {
        String message = this.brandService.update(brand, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}