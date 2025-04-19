package com.github.souzafcharles.rentalcar.controller;

import java.util.List;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import com.github.souzafcharles.rentalcar.service.AccessoryService;
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
@RequestMapping("/api/accessories")
@CrossOrigin("*")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Accessory>> findAll() {
        List<Accessory> list = this.accessoryService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<List<Accessory>> findByName(@RequestParam("name") String name) {
        List<Accessory> list = this.accessoryService.findByName(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Accessory> findById(@PathVariable("id") long id) { //
        Accessory accessory = this.accessoryService.findById(id);
        return new ResponseEntity<>(accessory, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        String message = this.accessoryService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Accessory accessory) {
        String message = this.accessoryService.save(accessory);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody Accessory accessory, @PathVariable("id") long id) {
        String message = this.accessoryService.update(accessory, id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}