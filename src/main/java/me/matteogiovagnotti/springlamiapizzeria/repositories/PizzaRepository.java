package me.matteogiovagnotti.springlamiapizzeria.repositories;

import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository <Pizza, Integer> {

    public List<Pizza> findByNameContainingIgnoreCase(String name);

}
