package org.java.lessons.spring_la_mia_pizzeria_crud.repo;

import org.java.lessons.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {   
}
