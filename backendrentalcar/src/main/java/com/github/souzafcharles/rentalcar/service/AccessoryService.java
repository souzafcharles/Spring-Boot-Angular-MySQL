package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import com.github.souzafcharles.rentalcar.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    public List<Accessory> findAll() {
        return this.accessoryRepository.findAll();
    }

    public Accessory findById(long id) {
        Optional<Accessory> accessory = this.accessoryRepository.findById(id);
        return accessory.orElse(null);
    }

    public List<Accessory> findByName(String name) {
        return this.accessoryRepository.findByNameContainingIgnoreCase(name);
    }

    public String save(Accessory accessory) {
        this.accessoryRepository.save(accessory);
        return "Accessory saved successfully";
    }

    public String update(Accessory accessory, long id) {
        accessory.setId(id);
        this.accessoryRepository.save(accessory);
        return "Accessory updated successfully";
    }

    public String deleteById(long id) {
        this.accessoryRepository.deleteById(id);
        return "Accessory deleted successfully";
    }

}