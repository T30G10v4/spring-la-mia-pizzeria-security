package me.matteogiovagnotti.springlamiapizzeria.controllers;


import jakarta.validation.Valid;
import me.matteogiovagnotti.springlamiapizzeria.exceptions.PizzaNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.AlertMessage;
import me.matteogiovagnotti.springlamiapizzeria.models.AlertMessage.AlertMessageType;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PizzaRepository;
import me.matteogiovagnotti.springlamiapizzeria.services.IngredientService;
import me.matteogiovagnotti.springlamiapizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model, @RequestParam(name = "name") Optional<String> keyword){

        List<Pizza> pizzas;
        if(keyword.isEmpty()) {

           pizzas = pizzaRepository.findAll(Sort.by("name"));

        } else {

            pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
            model.addAttribute("keyword", keyword.get());

        }

        model.addAttribute("list", pizzas);
        return "/pizzas/index";

    }

    @GetMapping("/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model){

        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/pizzas/show";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found.");

        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientsList", ingredientService.getAll());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza,
                           BindingResult bindingResult, Model model) {

        // VALIDATION
        if (bindingResult.hasErrors()) {
            // ritorno alla view con il form
            model.addAttribute("ingredientsList", ingredientService.getAll());
            return "/pizzas/create";
        }
        // se non ci sono errori procedo con la persistenza
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        Pizza pizza = pizzaService.getById(id);
        model.addAttribute("pizza", pizza);
        return "/pizzas/edit";

    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors())  return "/pizzas/edit";

        try {

            Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);
            return "redirect:/pizzas/" + Integer.toString(updatedPizza.getId());

        } catch(PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id "+id+" not found");
        }


    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){

        try {
            boolean success = pizzaService.deleteById(id);
            if(success){

                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza with id " + id + " deleted"));

            } else {

                redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.ERROR, "Unable to delete pizza with id = "+id));

            }
        } catch (PizzaNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.ERROR, "pizza with id = "+id+ " not found"));
        }

        return "redirect:/pizzas";

    }

}
