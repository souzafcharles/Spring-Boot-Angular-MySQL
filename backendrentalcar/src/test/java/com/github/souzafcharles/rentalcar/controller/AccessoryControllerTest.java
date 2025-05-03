package com.github.souzafcharles.rentalcar.controller;

import com.github.souzafcharles.rentalcar.entity.Accessory;
import com.github.souzafcharles.rentalcar.service.AccessoryService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccessoryControllerTest {

    @Mock
    private AccessoryService accessoryService;

    @InjectMocks
    private AccessoryController accessoryController;

    private MockMvc mockMvc;

    @Nested
    class findAll {

        @Test
        @DisplayName("Should return all accessories with status 200")
        void shouldReturnAllAccessories() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            var accessories = List.of(
                    new Accessory(1L, "Accessory1", "Description1"),
                    new Accessory(2L, "Accessory2", "Description2")
            );
            when(accessoryService.findAll()).thenReturn(accessories);

            // Act & Assert
            mockMvc.perform(get("/api/accessories/findAll")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Accessory1"));

            verify(accessoryService).findAll();
        }
    }

    @Nested
    class findByName {

        @Test
        @DisplayName("Should return accessories by name with status 200")
        void shouldReturnAccessoriesByName() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            var accessories = List.of(
                    new Accessory(1L, "Accessory1", "Description1")
            );
            when(accessoryService.findByName("Accessory")).thenReturn(accessories);

            // Act & Assert
            mockMvc.perform(get("/api/accessories/findByName")
                            .param("name", "Accessory")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.size()").value(1))
                    .andExpect(jsonPath("$[0].name").value("Accessory1"));

            verify(accessoryService).findByName("Accessory");
        }
    }

    @Nested
    class findById {

        @Test
        @DisplayName("Should return accessory by ID with status 200")
        void shouldReturnAccessoryById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            var accessory = new Accessory(1L, "Accessory1", "Description1");
            when(accessoryService.findById(1L)).thenReturn(accessory);

            // Act & Assert
            mockMvc.perform(get("/api/accessories/findById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Accessory1"));

            verify(accessoryService).findById(1L);
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete accessory by ID with status 200")
        void shouldDeleteAccessoryById() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            when(accessoryService.deleteById(1L)).thenReturn("Accessory deleted successfully");

            // Act & Assert
            mockMvc.perform(delete("/api/accessories/deleteById/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Accessory deleted successfully"));

            verify(accessoryService).deleteById(1L);
        }
    }

    @Nested
    class save {

        @Test
        @DisplayName("Should save accessory with status 200")
        void shouldSaveAccessory() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            when(accessoryService.save(any(Accessory.class))).thenReturn("Accessory saved successfully");

            // Act & Assert
            mockMvc.perform(post("/api/accessories/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"Accessory1\", \"description\":\"Description1\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Accessory saved successfully"));

            verify(accessoryService).save(any(Accessory.class));
        }
    }

    @Nested
    class update {

        @Test
        @DisplayName("Should update accessory by ID with status 200")
        void shouldUpdateAccessory() throws Exception {
            // Arrange
            mockMvc = MockMvcBuilders.standaloneSetup(accessoryController).build();
            when(accessoryService.update(any(Accessory.class), eq(1L))).thenReturn("Accessory updated successfully");

            // Act & Assert
            mockMvc.perform(put("/api/accessories/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"UpdatedAccessory\", \"description\":\"UpdatedDescription\"}"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Accessory updated successfully"));

            verify(accessoryService).update(any(Accessory.class), eq(1L));
        }
    }
}