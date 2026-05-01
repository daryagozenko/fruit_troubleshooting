package com.gozenko.service;

import com.gozenko.entity.Fruit;
import com.gozenko.repository.FruitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitService {
    private static final Logger logger = LoggerFactory.getLogger(FruitService.class);

    @Autowired
    private FruitRepository fruitRepository;

    public Fruit createFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    public Fruit getFruitById(Long id) {
        return fruitRepository.findById(id).orElse(null);
    }

    public List<Fruit> getAllFruitByName(String name) throws InterruptedException {
        Thread.sleep(1000);
        return fruitRepository.findAllByName(name);
    }
}
