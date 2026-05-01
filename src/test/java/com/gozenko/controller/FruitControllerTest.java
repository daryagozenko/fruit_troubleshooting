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
}