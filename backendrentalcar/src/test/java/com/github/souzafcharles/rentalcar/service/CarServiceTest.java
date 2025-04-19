package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import com.github.souzafcharles.rentalcar.entity.Brand;
import com.github.souzafcharles.rentalcar.entity.Car;
import com.github.souzafcharles.rentalcar.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all cars with accessories and brand")
        void shouldReturnAllCars() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description of Brand1");
            var accessories = List.of(new Accessory(1L, "Accessory1", "Description of Accessory1"));
            var carList = List.of(
                    new Car(1L, "Car1", "Model1", brand, accessories),
                    new Car(2L, "Car2", "Model2", brand, accessories)
            );
            when(carRepository.findAll()).thenReturn(carList);

            // Act
            var result = carService.findAll();

            // Assert
            assertEquals(2, result.size());
            assertEquals("Car1", result.get(0).getName());
            assertEquals("Accessory1", result.get(0).getAccessories().get(0).getName());
            verify(carRepository).findAll();
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return a car by ID including its brand and accessories")
        void shouldReturnCarById() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description of Brand1");
            var accessories = List.of(new Accessory(1L, "Accessory1", "Description of Accessory1"));
            var car = new Car(1L, "Car1", "Model1", brand, accessories);
            when(carRepository.findById(1L)).thenReturn(Optional.of(car));

            // Act
            var result = carService.findById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("Car1", result.getName());
            assertEquals("Brand1", result.getBrand().getName());
            verify(carRepository).findById(1L);
        }

        @Test
        @DisplayName("Should return null when car not found")
        void shouldReturnNullWhenCarNotFound() {
            // Arrange
            when(carRepository.findById(1L)).thenReturn(Optional.empty());

            // Act
            var result = carService.findById(1L);

            // Assert
            assertNull(result);
            verify(carRepository).findById(1L);
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return cars containing the specified name")
        void shouldReturnCarsByName() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description of Brand1");
            var accessories = List.of(new Accessory(1L, "Accessory1", "Description of Accessory1"));
            var carList = List.of(
                    new Car(1L, "Car1", "Model1", brand, accessories),
                    new Car(2L, "Car2", "Model2", brand, accessories)
            );
            when(carRepository.findByNameContainingIgnoreCase("Car")).thenReturn(carList);

            // Act
            var result = carService.findByName("Car");

            // Assert
            assertEquals(2, result.size());
            verify(carRepository).findByNameContainingIgnoreCase("Car");
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save a car successfully")
        void shouldSaveCarSuccessfully() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description of Brand1");
            var accessories = List.of(new Accessory(1L, "Accessory1", "Description of Accessory1"));
            var car = new Car(0L, "Car1", "Model1", brand, accessories);
            when(carRepository.save(car)).thenReturn(car);

            // Act
            var message = carService.save(car);

            // Assert
            assertEquals("Car saved successfully.", message);
            verify(carRepository).save(car);
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update a car's data successfully")
        void shouldUpdateCarSuccessfully() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description of Brand1");
            var accessories = List.of(new Accessory(1L, "Accessory1", "Description of Accessory1"));
            var car = new Car(1L, "UpdatedCar", "UpdatedModel", brand, accessories);
            when(carRepository.save(car)).thenReturn(car);

            // Act
            var message = carService.update(car, 1L);

            // Assert
            assertEquals("Car updated successfully.", message);
            verify(carRepository).save(car);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete a car by ID")
        void shouldDeleteCarByIdSuccessfully() {
            // Arrange
            doNothing().when(carRepository).deleteById(1L);

            // Act
            var message = carService.deleteById(1L);

            // Assert
            assertEquals("Car deleted successfully.", message);
            verify(carRepository).deleteById(1L);
        }
    }
}