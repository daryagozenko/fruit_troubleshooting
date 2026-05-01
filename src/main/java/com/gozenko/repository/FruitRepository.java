package com.gozenko.repository;

import com.gozenko.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    @Query("SELECT f FROM Fruit f WHERE LOWER(f.name) = LOWER(:name)")
    List<Fruit> findAllByName(@Param("name") String name);
}
