package me.matteogiovagnotti.springlamiapizzeria.services;

import me.matteogiovagnotti.springlamiapizzeria.exceptions.PizzaNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formPizza){

        Pizza pizzaToPersist = new Pizza();

        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(formPizza.getPrice());
        pizzaToPersist.setIngredients(formPizza.getIngredients());

        return pizzaRepository.save(pizzaToPersist);

    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws PizzaNotFoundException {

        Pizza pizzaToUpdate = getById(id);
        pizzaToUpdate.setName(formPizza.getName());
        pizzaToUpdate.setDescription(formPizza.getDescription());
        pizzaToUpdate.setPrice(formPizza.getPrice());
        pizzaToUpdate.setIngredients(formPizza.getIngredients());
        return pizzaRepository.save(pizzaToUpdate);

    }

    public Pizza getById(Integer id) throws PizzaNotFoundException {

        Optional<Pizza> result = pizzaRepository.findById(id);
        if(result.isPresent()) return result.get();
        else throw new PizzaNotFoundException(Integer.toString(id));

    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            return false;

        }
    }

}
