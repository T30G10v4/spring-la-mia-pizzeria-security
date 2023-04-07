package me.matteogiovagnotti.springlamiapizzeria.repositories;

import me.matteogiovagnotti.springlamiapizzeria.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
