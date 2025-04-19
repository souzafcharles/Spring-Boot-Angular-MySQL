package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Brand;
import com.github.souzafcharles.rentalcar.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        return this.brandRepository.findAll();
    }

    public Brand findById(long id) {
        Optional<Brand> brand = this.brandRepository.findById(id);
        return brand.orElse(null);
    }

    public List<Brand> findByName(String name) {
        return this.brandRepository.findByNameContainingIgnoreCase(name);
    }

    public String save(Brand brand) {
        this.brandRepository.save(brand);
        return "Brand saved successfully.";
    }

    public String update(Brand brand, long id) {
        brand.setId(id);
        this.brandRepository.save(brand);
        return "Brand updated successfully.";
    }

    public String deleteById(long id) {
        this.brandRepository.deleteById(id);
        return "Brand deleted successfully.";
    }

}