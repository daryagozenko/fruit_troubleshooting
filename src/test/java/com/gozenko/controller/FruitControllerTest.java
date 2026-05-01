package com.gozenko.controller;

import com.gozenko.entity.Fruit;
import com.gozenko.service.FruitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitControllerTest {

    @Mock
    private FruitService fruitService;

    @InjectMocks
    private FruitController fruitController;

    private Fruit testFruit;

    @BeforeEach
    void setUp() {
        testFruit = new Fruit(1L, "Яблоко", 150);
    }

    @Test
    void createFruit_ShouldReturnCreatedFruit() {
        when(fruitService.createFruit(any(Fruit.class))).thenReturn(testFruit);

        ResponseEntity<Fruit> response = fruitController.createFruit(testFruit);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Яблоко", Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void getAllFruits_ShouldReturnListOfFruits() {
        List<Fruit> fruits = Arrays.asList(testFruit, new Fruit(2L, "Банан", 120));
        when(fruitService.getAllFruits()).thenReturn(fruits);

        ResponseEntity<List<Fruit>> response = fruitController.getAllFruits();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void getFruitById_WhenFruitExists_ShouldReturnFruit() {
        when(fruitService.getFruitById(1L)).thenReturn(testFruit);

        ResponseEntity<Fruit> response = fruitController.getFruitById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Яблоко", response.getBody().getName());
        assertEquals(150, response.getBody().getPrice());
    }

    @Test
    void getFruitById_WhenFruitDoesNotExist_ShouldReturnNotFound() {
        when(fruitService.getFruitById(99L)).thenReturn(null);

        ResponseEntity<Fruit> response = fruitController.getFruitById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllFruitByName_WhenFruitsExist_ShouldReturnFruits() throws InterruptedException {
        List<Fruit> fruits = Arrays.asList(testFruit, testFruit);
        when(fruitService.getAllFruitByName("Яблоко")).thenReturn(fruits);

        ResponseEntity<List<Fruit>> response = fruitController.getAllFruitByName("Яблоко");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void getAllFruitByName_WhenNameIsWhitespace_ShouldReturnBadRequest() throws InterruptedException {
        ResponseEntity<List<Fruit>> response = fruitController.getAllFruitByName("   ");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}