package edu.ncsu.csc.CoffeeMaker.models;

import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
     * Ingredient type
     */
    @Enumerated(EnumType.STRING)
    private IngredientType ingredient;

    /**
     * Ingredient amount
     */
    @Min(0)
    private Integer amount;

    /**
     * Creates a default Ingredient for the CoffeeMaker
     */
    public Ingredient() {
        this.ingredient = IngredientType.COFFEE;
        this.amount = 0;
    }

    /**
     * Creates a specific Ingredient for the CoffeeMaker
     * @param ingredient type of the ingredient to create
     * @param amount amount of ingredient
     */
    public Ingredient(IngredientType ingredient, Integer amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    /**
     * Gets the type of the Ingredient
     * @return the ingredient type
     */
    public IngredientType getIngredient() {
        return this.ingredient;
    }

    /**
     * Sets the type of the Ingredient
     * @param ingredient type to be set
     */
    public void setIngredient(IngredientType ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Gets the amount of the Ingredient
     * @return the amount of the Ingredient
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of the Ingredient
     * @param amount of the ingredient
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
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
                ", amount=" + amount +
                '}';
    }

}
