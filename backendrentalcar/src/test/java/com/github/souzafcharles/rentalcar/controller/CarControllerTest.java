package com.github.souzafcharles.rentalcar.controller;

import com.github.souzafcharles.rentalcar.entity.Car;
import com.github.souzafcharles.rentalcar.service.CarService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all cars with status 200")
        void shouldReturnAllCars() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            var cars = List.of(
                    new Car(1L, "Car1", "Model1", null, null),
                    new Car(2L, "Car2", "Model2", null, null)
            );
            when(carService.findAll()).thenReturn(cars);

            // Act & Assert
            mockMvc.perform(get("/api/cars/findAll")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Car1"));

            verify(carService).findAll();
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return cars by name with status 200")
        void shouldReturnCarsByName() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            var cars = List.of(
                    new Car(1L, "Car1", "Model1", null, null)
            );
            when(carService.findByName("Car")).thenReturn(cars);

            // Act & Assert
            mockMvc.perform(get("/api/cars/findByName")
                            .param("name", "Car")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].name").value("Car1"));

            verify(carService).findByName("Car");
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return car by ID with status 200")
        void shouldReturnCarById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            var car = new Car(1L, "Car1", "Model1", null, null);
            when(carService.findById(1L)).thenReturn(car);

            // Act & Assert
            mockMvc.perform(get("/api/cars/findById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Car1"));

            verify(carService).findById(1L);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete car by ID with status 200")
        void shouldDeleteCarById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            when(carService.deleteById(1L)).thenReturn("Car deleted successfully");

            // Act & Assert
            mockMvc.perform(delete("/api/cars/deleteById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Car deleted successfully"));

            verify(carService).deleteById(1L);
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save car with status 200")
        void shouldSaveCar() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            when(carService.save(any(Car.class))).thenReturn("Car saved successfully");

            // Act & Assert
            mockMvc.perform(post("/api/cars/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Car1\", \"model\":\"Model1\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Car saved successfully"));

            verify(carService).save(any(Car.class));
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update car by ID with status 200")
        void shouldUpdateCar() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
            when(carService.update(any(Car.class), eq(1L))).thenReturn("Car updated successfully");

            // Act & Assert
            mockMvc.perform(put("/api/cars/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"UpdatedCar\", \"model\":\"UpdatedModel\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Car updated successfully"));

            verify(carService).update(any(Car.class), eq(1L));
        }
    }
}