package org.java.lessons.spring_la_mia_pizzeria_crud.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.java.lessons.spring_la_mia_pizzeria_crud.model.Offer;
import org.java.lessons.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.lessons.spring_la_mia_pizzeria_crud.repo.OfferRepository;
import org.java.lessons.spring_la_mia_pizzeria_crud.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {

         List<Pizza> pizzas;
         
        if (keyword != null && !keyword.isEmpty()) {

            pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword);

        } else {

           pizzas = pizzaRepository.findAll();

        }

        model.addAttribute("pizzas", pizzas);

        return "pizzas/index";

    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "pizzas/show";

    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("pizza", new Pizza());

        return "pizzas/create";

    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult ,Model model){

        if (bindingResult.hasErrors()) {
            
            return "pizzas/create";

        }
 
        pizzaRepository.save(formPizza);

        return "redirect:/pizzas";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){

        model.addAttribute("pizza", pizzaRepository.findById(id).get());

        return "pizzas/edit";

    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult ,Model model){

        if (bindingResult.hasErrors()) {
            
            return "pizzas/edit";

        }
 
        pizzaRepository.save(formPizza);

        return "redirect:/pizzas";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model){

        Pizza pizzaToDelete = pizzaRepository.findById(id).get();
        for (Offer offer : pizzaToDelete.getOffers()) {
            offerRepository.delete(offer);
        }

        pizzaRepository.delete(pizzaToDelete);;

        return "redirect:/pizzas";

    }

    @GetMapping("/{id}/offer")
    public String offer(@PathVariable("id") Integer id, Model model){

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no pizza with id" + id);
        }

        model.addAttribute("pizza", pizzaOptional.get());
        Offer offer = new Offer();
        offer.setPizza(pizzaOptional.get());
        offer.setOfferStartDate(LocalDate.now());;
        model.addAttribute("offer", offer);

        return "offers/create";

    }
}
