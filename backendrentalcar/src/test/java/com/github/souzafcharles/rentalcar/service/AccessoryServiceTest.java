package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import com.github.souzafcharles.rentalcar.repository.AccessoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccessoryServiceTest {

    @Mock
    private AccessoryRepository accessoryRepository;

    @InjectMocks
    private AccessoryService accessoryService;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all accessories")
        void shouldReturnAllAccessories() {
            // Arrange
            var accessories = List.of(
                    new Accessory(1L, "Accessory1", "Description1"),
                    new Accessory(2L, "Accessory2", "Description2")
            );
            when(accessoryRepository.findAll()).thenReturn(accessories);

            // Act
            var result = accessoryService.findAll();

            // Assert
            assertEquals(2, result.size());
            assertEquals("Accessory1", result.get(0).getName());
            verify(accessoryRepository).findAll();
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return accessory by ID")
        void shouldReturnAccessoryById() {
            // Arrange
            var accessory = new Accessory(1L, "Accessory1", "Description1");
            when(accessoryRepository.findById(1L)).thenReturn(Optional.of(accessory));

            // Act
            var result = accessoryService.findById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("Accessory1", result.getName());
            verify(accessoryRepository).findById(1L);
        }

        @Test
        @DisplayName("Should return null when accessory not found")
        void shouldReturnNullWhenAccessoryNotFound() {
            // Arrange
            when(accessoryRepository.findById(1L)).thenReturn(Optional.empty());

            // Act
            var result = accessoryService.findById(1L);

            // Assert
            assertNull(result);
            verify(accessoryRepository).findById(1L);
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return accessories containing specified name")
        void shouldReturnAccessoriesByName() {
            // Arrange
            var accessories = List.of(
                    new Accessory(1L, "Accessory1", "Description1"),
                    new Accessory(2L, "Accessory2", "Description2")
            );
            when(accessoryRepository.findByNameContainingIgnoreCase("Accessory")).thenReturn(accessories);

            // Act
            var result = accessoryService.findByName("Accessory");

            // Assert
            assertEquals(2, result.size());
            verify(accessoryRepository).findByNameContainingIgnoreCase("Accessory");
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save an accessory successfully")
        void shouldSaveAccessorySuccessfully() {
            // Arrange
            var accessory = new Accessory(0L, "Accessory1", "Description1");
            when(accessoryRepository.save(accessory)).thenReturn(accessory);

            // Act
            var message = accessoryService.save(accessory);

            // Assert
            assertEquals("Accessory saved successfully", message);
            verify(accessoryRepository).save(accessory);
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update an accessory's data successfully")
        void shouldUpdateAccessorySuccessfully() {
            // Arrange
            var accessory = new Accessory(1L, "UpdatedAccessory", "UpdatedDescription");
            when(accessoryRepository.save(accessory)).thenReturn(accessory);

            // Act
            var message = accessoryService.update(accessory, 1L);

            // Assert
            assertEquals("Accessory updated successfully", message);
            verify(accessoryRepository).save(accessory);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete an accessory by ID")
        void shouldDeleteAccessoryByIdSuccessfully() {
            // Arrange
            doNothing().when(accessoryRepository).deleteById(1L);

            // Act
            var message = accessoryService.deleteById(1L);

            // Assert
            assertEquals("Accessory deleted successfully", message);
            verify(accessoryRepository).deleteById(1L);
        }
    }
}