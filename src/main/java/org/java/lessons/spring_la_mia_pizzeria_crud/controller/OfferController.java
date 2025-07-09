package org.java.lessons.spring_la_mia_pizzeria_crud.controller;

import org.java.lessons.spring_la_mia_pizzeria_crud.model.Offer;
import org.java.lessons.spring_la_mia_pizzeria_crud.repo.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @GetMapping
    public String index(Model model){

        model.addAttribute("offers", offerRepository.findAll());
        return "offers/index";

    }

    @PostMapping
    public String store(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("offer", offerForm);
            return "offers/create";
            
        }

        offerRepository.save(offerForm);

        return "redirect:/pizzas"; 

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        offerRepository.deleteById(id);

        return "redirect:/pizzas";

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model){

        model.addAttribute("offer", offerRepository.findById(id).get());
        return "offers/edit";

    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("offer", offerForm);
            return "offers/edit";
            
        }

        offerRepository.save(offerForm);
        return "redirect:/pizzas";

    }
}
