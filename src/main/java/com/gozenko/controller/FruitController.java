package com.gozenko.controller;

import com.gozenko.entity.Fruit;
import com.gozenko.service.FruitService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    private static final Logger logger = LoggerFactory.getLogger(FruitController.class);

    @Autowired
    private FruitService fruitService;

    @PostMapping
    @Operation(summary = "создать новый фрукт")
    public ResponseEntity<Fruit> createFruit(@RequestBody Fruit fruit) {
        logger.info("Получен запрос на создание фрукта: {}", fruit.getName());

        logger.debug("Детали запроса: тело запроса = {}", fruit);

        try {
            Fruit createdFruit = fruitService.createFruit(fruit);

            logger.info("Фрукт успешно создан с ID: {}", createdFruit.getId());

            logger.trace("Полный объект созданного фрукта: {}", createdFruit);

            return new ResponseEntity<>(createdFruit, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error("Ошибка при создании фрукта: {}", fruit.getName(), e);
            throw e;
        }
    }

    @GetMapping("/")
    @Operation(summary = "получить все фрукты")
    public ResponseEntity<List<Fruit>> getAllFruits() {
        logger.debug("Запрос на получение всех фруктов");

        List<Fruit> fruits = fruitService.getAllFruits();
        logger.info("Получено {} фруктов из базы данных", fruits.size());

        return ResponseEntity.ok(fruits);
    }

    @GetMapping("/{id}")
    @Operation(summary = "получить фрукт по id")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        logger.trace("Вход в метод getFruitById с параметром id={}", id);

        logger.debug("Поиск фрукта с ID: {}", id);

        Fruit fruit = fruitService.getFruitById(id);

        if (fruit != null) {
            logger.info("Найден фрукт с ID {}: {}", id, fruit.getName());

            logger.debug("Детали фрукта: цена = {}, наличие в БД = true", fruit.getPrice());

            return ResponseEntity.ok(fruit);
        } else {
            logger.warn("Фрукт с ID {} не найден в базе данных", id);

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all/name/{name}")
    @Operation(summary = "получить все фрукты по имени")
    public ResponseEntity<List<Fruit>> getAllFruitByName(@PathVariable("name") String name) {
        logger.info("Запрос на поиск фруктов по имени: '{}'", name);

        logger.debug("Параметры запроса: name={}, длина имени={}", name, name.length());


        if (name.trim().isEmpty()) {
            logger.warn("Попытка поиска с пустым или null именем");
            return ResponseEntity.badRequest().build();
        }

        List<Fruit> fruit;
        try {
            logger.debug("Вызов fruitService.getAllFruitByName('{}')", name);

            fruit = fruitService.getAllFruitByName(name);

            logger.trace("Метод сервиса вернул коллекцию с {} элементами",
                    fruit != null ? fruit.size() : 0);

        } catch (InterruptedException ex) {
            logger.error("Прервано выполнение потока при поиске фруктов по имени: {}", name, ex);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ошибка при поиске фруктов", ex);
        }

        if (fruit != null && !fruit.isEmpty()) {
            logger.info("Найдено {} фруктов с именем '{}'", fruit.size(), name);

            if (!fruit.isEmpty()) {
                List<Long> fruitIds = fruit.stream()
                        .map(Fruit::getId)
                        .toList();
                logger.debug("ID найденных фруктов: {}", fruitIds);
            }

            return ResponseEntity.ok(fruit);
        } else {
            logger.warn("Фрукты с именем '{}' не найдены", name);

            return ResponseEntity.notFound().build();
        }
    }
}