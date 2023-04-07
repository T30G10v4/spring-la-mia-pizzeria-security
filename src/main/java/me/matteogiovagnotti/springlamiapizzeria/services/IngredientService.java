package me.matteogiovagnotti.springlamiapizzeria.services;

import me.matteogiovagnotti.springlamiapizzeria.models.Ingredient;
import me.matteogiovagnotti.springlamiapizzeria.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {

        return ingredientRepository.findAll(Sort.by("name"));

    }

    public Ingredient Create(Ingredient formIngredient) {

        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredient.getName());
        ingredientToCreate.setDescription(formIngredient.getDescription());
        return ingredientRepository.save(ingredientToCreate);

    }
}
