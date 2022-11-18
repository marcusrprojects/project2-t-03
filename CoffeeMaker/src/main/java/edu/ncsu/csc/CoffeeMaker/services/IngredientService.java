package edu.ncsu.csc.CoffeeMaker.services;

import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * The IngredientService is used to handle CRUD operations on the Ingredient
 * model.
 *
 * @author Darien Gillespie
 *
 */
@Component
@Transactional
public class IngredientService extends Service {

    /**
     * IngredientRepository, to be autowired in by Spring and provide CRUD
     * operations on Ingredient model.
     */
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    protected JpaRepository getRepository() {
        return this.ingredientRepository;
    }
}
