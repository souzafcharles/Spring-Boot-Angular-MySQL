package com.github.souzafcharles.rentalcar.controller;

import com.github.souzafcharles.rentalcar.entity.Brand;
import com.github.souzafcharles.rentalcar.service.BrandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @InjectMocks
    private BrandController brandController;

    private MockMvc mockMvc;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all brands with status 200")
        void shouldReturnAllBrands() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            var brands = List.of(
                    new Brand(1L, "Brand1", "Description1"),
                    new Brand(2L, "Brand2", "Description2")
            );
            when(brandService.findAll()).thenReturn(brands);

            // Act & Assert
            mockMvc.perform(get("/api/brands/findAll")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Brand1"));

            verify(brandService).findAll();
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return brands by name with status 200")
        void shouldReturnBrandsByName() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            var brands = List.of(
                    new Brand(1L, "Brand1", "Description1")
            );
            when(brandService.findByName("Brand")).thenReturn(brands);

            // Act & Assert
            mockMvc.perform(get("/api/brands/findByName")
                            .param("name", "Brand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].name").value("Brand1"));

            verify(brandService).findByName("Brand");
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return brand by ID with status 200")
        void shouldReturnBrandById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            var brand = new Brand(1L, "Brand1", "Description1");
            when(brandService.findById(1L)).thenReturn(brand);

            // Act & Assert
            mockMvc.perform(get("/api/brands/findById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Brand1"));

            verify(brandService).findById(1L);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete brand by ID with status 200")
        void shouldDeleteBrandById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            when(brandService.deleteById(1L)).thenReturn("Brand deleted successfully");

            // Act & Assert
            mockMvc.perform(delete("/api/brands/deleteById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Brand deleted successfully"));

            verify(brandService).deleteById(1L);
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save brand with status 200")
        void shouldSaveBrand() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            when(brandService.save(any(Brand.class))).thenReturn("Brand saved successfully");

            // Act & Assert
            mockMvc.perform(post("/api/brands/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Brand1\", \"description\":\"Description1\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Brand saved successfully"));

            verify(brandService).save(any(Brand.class));
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update brand by ID with status 200")
        void shouldUpdateBrand() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
            when(brandService.update(any(Brand.class), eq(1L))).thenReturn("Brand updated successfully");

            // Act & Assert
            mockMvc.perform(put("/api/brands/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"UpdatedBrand\", \"description\":\"UpdatedDescription\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Brand updated successfully"));

            verify(brandService).update(any(Brand.class), eq(1L));
        }
    }
}