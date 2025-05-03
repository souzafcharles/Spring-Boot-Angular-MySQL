package com.github.souzafcharles.rentalcar.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Nested
    class HandleMethodArgumentNotValid {

        @Test
        @DisplayName("Should return BAD_REQUEST and field errors")
        void shouldReturnBadRequestWithFieldErrors() {
            // Arrange
            var fieldError1 = new FieldError("Car", "name", "Name cannot be empty");
            var fieldError2 = new FieldError("Car", "model", "Model cannot be null");
            var bindingResult = mock(org.springframework.validation.BindingResult.class);

            when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
            var exception = new MethodArgumentNotValidException(null, bindingResult);

            // Act
            ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleMethodArgumentNotValid(exception);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(2, response.getBody().size());
            assertEquals("Name cannot be empty", response.getBody().get("name"));
            assertEquals("Model cannot be null", response.getBody().get("model"));
        }
    }

    @Nested
    class HandleConstraintViolation {

        @Test
        @DisplayName("Should return BAD_REQUEST and validation messages")
        void shouldReturnBadRequestWithValidationMessages() {
            // Arrange
            var violation1 = mock(ConstraintViolation.class);
            var violation2 = mock(ConstraintViolation.class);
            var path1 = mock(Path.class);
            var path2 = mock(Path.class);

            when(violation1.getPropertyPath()).thenReturn(path1);
            when(path1.toString()).thenReturn("car.name");
            when(violation1.getMessage()).thenReturn("Invalid car name");

            when(violation2.getPropertyPath()).thenReturn(path2);
            when(path2.toString()).thenReturn("car.model");
            when(violation2.getMessage()).thenReturn("Invalid car model");

            Set<ConstraintViolation<?>> violations = new HashSet<>();
            violations.add(violation1);
            violations.add(violation2);
            var exception = new ConstraintViolationException(violations);

            // Act
            ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleConstraintViolation(exception);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertEquals(2, response.getBody().size());
            assertEquals("Invalid car name", response.getBody().get("car.name"));
            assertEquals("Invalid car model", response.getBody().get("car.model"));
        }
    }

    @Nested
    class HandleGenericException {

        @Test
        @DisplayName("Should return BAD_REQUEST and exception message")
        void shouldReturnBadRequestWithExceptionMessage() {
            // Arrange
            var exception = new RuntimeException("Unexpected error occurred");

            // Act
            ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

            // Assert
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("Unexpected error occurred"));
        }
    }
}