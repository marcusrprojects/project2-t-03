package edu.ncsu.csc.CoffeeMaker.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Ingredient for the coffee maker. Ingredient is tied to the database using Hibernate
 * libraries. See IngredientRepository and IngredientService for the other two pieces
 * used for database support.
 *
 * @author Darien Gillespie
 */
@Entity
public class Ingredient extends DomainObject {

    /**
     * Ingredient id
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Ingredient name
     */
    private String ingredient;

    /**
     * Creates a default Ingredient for the CoffeeMaker
     */
    public Ingredient() {
        this.ingredient = "";
    }

    /**
     * Creates a specific Ingredient for the CoffeeMaker
     *
     * @param ingredient type of the ingredient to create
     */
    public Ingredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Gets the type of the Ingredient
     *
     * @return the ingredient type
     */
    public String getIngredient() {
        return this.ingredient;
    }

    /**
     * Sets the type of the Ingredient
     *
     * @param ingredient type to be set
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                '}';
    }

}
