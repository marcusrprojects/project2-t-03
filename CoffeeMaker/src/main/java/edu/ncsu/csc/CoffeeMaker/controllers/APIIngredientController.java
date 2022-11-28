package edu.ncsu.csc.CoffeeMaker.controllers;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class APIIngredientController extends APIController {
    /**
     * IngredientService object, to be autowired in by Spring to allow for
     * manipulating the Ingredient model
     */
    @Autowired
    private IngredientService ingredientService;

    /**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return JSON representation of all ingredients
     */
    @GetMapping(BASE_PATH + "/ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientService.findAll();
    }

/*
    @GetMapping(BASE_PATH + "ingredients/{name}")
    public ResponseEntity getIngredient(@PathVariable final String name) {

        final Ingredient ingr = ingredientService.findByName(name);

        if (null == ingr) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(ingr, HttpStatus.OK);
    }
*/

    /**
     * REST API method to provide POST access to the Ingredient model. This is used
     * to create a new Ingredient by automatically converting the JSON RequestBody
     * provided to an Ingredient object. Invalid JSON will fail.
     *
     * @param ingredient The valid Ingredient to be saved.
     * @return ResponseEntity indicating success if the Ingredient could be saved to
     * the inventory, or an error if it could not be
     */
    @PostMapping(BASE_PATH + "/ingredients")
    public ResponseEntity createIngredient(@RequestBody final Ingredient ingredient) {

        ingredientService.save(ingredient);
        return new ResponseEntity(successResponse(ingredient.toString() + " successfully created"), HttpStatus.OK);
    }
/*
    *//**
     * REST API method to allow deleting an Ingredient from the CoffeeMaker's
     * Inventory, by making a DELETE request to the API endpoint and indicating
     * the ingredient to delete (as a path variable)
     *
     * @param name The name of the Ingredient to delete
     * @return Success if the ingredient could be deleted; an error if the ingredient
     * does not exist
     *//*
    @DeleteMapping(BASE_PATH + "/ingredients/{name}")
    public ResponseEntity deleteIngredient(@PathVariable final String name) {
        final Ingredient ingredient = ingredientService.findByName(name);
        if (null == ingredient) {
            return new ResponseEntity(errorResponse("No ingredient found for name " + name), HttpStatus.NOT_FOUND);
        }
        ingredientService.delete(ingredient);

        return new ResponseEntity(successResponse(name + " was deleted successfully"), HttpStatus.OK);
    }
*/
}