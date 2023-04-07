package me.matteogiovagnotti.springlamiapizzeria.controllers;

import jakarta.validation.Valid;
import me.matteogiovagnotti.springlamiapizzeria.exceptions.PizzaNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.exceptions.PromoNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.AlertMessage;
import me.matteogiovagnotti.springlamiapizzeria.models.Pizza;
import me.matteogiovagnotti.springlamiapizzeria.models.Promo;
import me.matteogiovagnotti.springlamiapizzeria.services.PizzaService;
import me.matteogiovagnotti.springlamiapizzeria.services.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/promos")
public class PromoController {

    @Autowired
    private PromoService promoService;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId")Optional<Integer> id, Model model){

        Promo promo = new Promo();
        promo.setBeginDate(LocalDate.now());
        promo.setEndDate(LocalDate.now().plusMonths(1));

        if(id.isPresent()) {

            try {
                Pizza pizza = pizzaService.getById(id.get());
                promo.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }

        model.addAttribute("promo", promo);
        return "/promos/create";

    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute Promo formPromo, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) return "/promos/create";

        Promo createdPromo = promoService.create(formPromo);
        return "redirect:/pizzas/" + Integer.toString(createdPromo.getPizza().getId());


    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        try {
            Promo promo = promoService.getById(id);
            model.addAttribute("promo", promo);
            return "/promos/edit";
        } catch (PromoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute Promo formPromo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {

            return "/promos/edit";

        }

        try {
            Promo updatedPromo = promoService.update(id, formPromo);
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Promo successfully updated"));
            return "redirect:/pizzas/"+updatedPromo.getPizza().getId();
        } catch (PromoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, @RequestParam("pizzaId") Optional<Integer> pizzaIdParam, RedirectAttributes redirectAttributes) {

        Integer pizzaId = pizzaIdParam.get();

        try {
            promoService.delete(id);
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Promo succesfully deleted"));
        } catch (PromoNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Promo with id "+e.getMessage()+ " not found"));
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Unable to delete Promo"));

        }

        if (pizzaId == null) return "redirect:/pizzas";
        return "redirect:/pizzas/"+Integer.toString(pizzaId);

    }

}
