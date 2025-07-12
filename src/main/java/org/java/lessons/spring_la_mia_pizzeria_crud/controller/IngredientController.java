package org.java.lessons.spring_la_mia_pizzeria_crud.controller;

import org.java.lessons.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.java.lessons.spring_la_mia_pizzeria_crud.model.Pizza;
import org.java.lessons.spring_la_mia_pizzeria_crud.repo.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("ingredients", ingredientRepository.findAll());

        return "ingredients/index";

    }

    @GetMapping("{id}")
    public String show(@PathVariable Integer id, Model model) {

        model.addAttribute("ingredient", ingredientRepository.findById(id).get());

        return "ingredients/show";

    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/create";

    }

    @PostMapping()
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            return "ingredients/create ";

        }

        ingredientRepository.save(formIngredient);

        return "redirect:/ingredients";

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {

        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        return "ingredients/edit";

    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredient", formIngredient);
            return "ingredients/edit";

        }

        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        Ingredient ingredientToDelete = ingredientRepository.findById(id).get();

        for (Pizza linkedPizza : ingredientToDelete.getPizzas()){
            linkedPizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.deleteById(id);
        return "redirect:/ingredients";
    }

}
