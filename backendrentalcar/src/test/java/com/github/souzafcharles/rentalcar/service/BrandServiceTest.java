package com.github.souzafcharles.rentalcar.service;

import com.github.souzafcharles.rentalcar.entity.Brand;
import com.github.souzafcharles.rentalcar.repository.BrandRepository;
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
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all brands")
        void shouldReturnAllBrands() {
            // Arrange
            var brands = List.of(
                    new Brand(1L, "Brand1", "Description1"),
                    new Brand(2L, "Brand2", "Description2")
            );
            when(brandRepository.findAll()).thenReturn(brands);

            // Act
            var result = brandService.findAll();

            // Assert
            assertEquals(2, result.size());
            assertEquals("Brand1", result.get(0).getName());
            verify(brandRepository).findAll();
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return brand by ID")
        void shouldReturnBrandById() {
            // Arrange
            var brand = new Brand(1L, "Brand1", "Description1");
            when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

            // Act
            var result = brandService.findById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("Brand1", result.getName());
            verify(brandRepository).findById(1L);
        }

        @Test
        @DisplayName("Should return null when brand not found")
        void shouldReturnNullWhenBrandNotFound() {
            // Arrange
            when(brandRepository.findById(1L)).thenReturn(Optional.empty());

            // Act
            var result = brandService.findById(1L);

            // Assert
            assertNull(result);
            verify(brandRepository).findById(1L);
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return brands containing specified name")
        void shouldReturnBrandsByName() {
            // Arrange
            var brands = List.of(
                    new Brand(1L, "Brand1", "Description1"),
                    new Brand(2L, "Brand2", "Description2")
            );
            when(brandRepository.findByNameContainingIgnoreCase("Brand")).thenReturn(brands);

            // Act
            var result = brandService.findByName("Brand");

            // Assert
            assertEquals(2, result.size());
            verify(brandRepository).findByNameContainingIgnoreCase("Brand");
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save a brand successfully")
        void shouldSaveBrandSuccessfully() {
            // Arrange
            var brand = new Brand(0L, "Brand1", "Description1");
            when(brandRepository.save(brand)).thenReturn(brand);

            // Act
            var message = brandService.save(brand);

            // Assert
            assertEquals("Brand saved successfully.", message);
            verify(brandRepository).save(brand);
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update a brand's data successfully")
        void shouldUpdateBrandSuccessfully() {
            // Arrange
            var brand = new Brand(1L, "UpdatedBrand", "UpdatedDescription");
            when(brandRepository.save(brand)).thenReturn(brand);

            // Act
            var message = brandService.update(brand, 1L);

            // Assert
            assertEquals("Brand updated successfully.", message);
            verify(brandRepository).save(brand);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete a brand by ID")
        void shouldDeleteBrandByIdSuccessfully() {
            // Arrange
            doNothing().when(brandRepository).deleteById(1L);

            // Act
            var message = brandService.deleteById(1L);

            // Assert
            assertEquals("Brand deleted successfully.", message);
            verify(brandRepository).deleteById(1L);
        }
    }
}