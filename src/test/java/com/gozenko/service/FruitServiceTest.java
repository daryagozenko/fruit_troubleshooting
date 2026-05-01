package com.gozenko.service;

import com.gozenko.entity.Fruit;
import com.gozenko.repository.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitService fruitService;

    private Fruit testFruit;

    @BeforeEach
    void setUp() {
        testFruit = new Fruit(1L, "Яблоко", 150);
    }

    @Test
    void createFruit_ShouldReturnSavedFruit() {
        when(fruitRepository.save(any(Fruit.class))).thenReturn(testFruit);

        Fruit result = fruitService.createFruit(testFruit);

        assertEquals("Яблоко", result.getName());
        assertEquals(150, result.getPrice());
    }

    @Test
    void getAllFruits_ShouldReturnListOfFruits() {
        List<Fruit> fruits = Arrays.asList(
                testFruit,
                new Fruit(2L, "Банан", 120)
        );
        when(fruitRepository.findAll()).thenReturn(fruits);

        List<Fruit> result = fruitService.getAllFruits();

        assertEquals(2, result.size());
    }

    @Test
    void getFruitById_WhenFruitExists_ShouldReturnFruit() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(testFruit));

        Fruit result = fruitService.getFruitById(1L);

        assertEquals("Яблоко", result.getName());
    }

    @Test
    void getFruitById_WhenFruitDoesNotExist_ShouldReturnNull() {
        when(fruitRepository.findById(99L)).thenReturn(Optional.empty());

        Fruit result = fruitService.getFruitById(99L);

        assertNull(result);
    }

    @Test
    void getAllFruitByName_ShouldReturnFruits() throws InterruptedException {
        List<Fruit> fruits = List.of(testFruit);
        when(fruitRepository.findAllByName("Яблоко")).thenReturn(fruits);

        List<Fruit> result = fruitService.getAllFruitByName("Яблоко");

        assertEquals(1, result.size());
        assertEquals("Яблоко", result.getFirst().getName());
    }

    @Test
    void getAllFruitByName_WhenNoFruitsFound_ShouldReturnEmptyList() throws InterruptedException {
        when(fruitRepository.findAllByName("Ананас")).thenReturn(List.of());

        List<Fruit> result = fruitService.getAllFruitByName("Ананас");

        assertTrue(result.isEmpty());
    }
}