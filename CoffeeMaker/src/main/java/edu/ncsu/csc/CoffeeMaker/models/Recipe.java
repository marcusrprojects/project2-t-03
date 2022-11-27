package edu.ncsu.csc.CoffeeMaker.models;

import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe for the coffee maker. Recipe is tied to the database using Hibernate
 * libraries. See RecipeRepository and RecipeService for the other two pieces
 * used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Recipe extends DomainObject {

    /**
     * Recipe id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Recipe name
     */
    private String name;

    /**
     * Recipe price
     */
    @Min(0)
    private Integer price;

    /**
     * List of Recipe Ingredients
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Ingredient> ingredients;

    /**
     * Creates a default recipe for the coffee maker.
     */
    public Recipe() {
        this.name = "";
        this.ingredients = new ArrayList<>();
    }

    /**
     * Adds a single Ingredient to the Recipe
     * @param ingredient to be added
     */
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    /**
     * Gets all Ingredients in the Recipe
     * @return list of Ingredients in the Recipe
     */
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    /**
     * Gets an Ingredient in the Recipe
     * @param ingredientType the type of the Ingredient desired
     * @return desired Ingredient in the Recipe, or null if it's not in the recipe
     */
    public Ingredient getIngredient(IngredientType ingredientType) {
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.getIngredient() == ingredientType) {
                return ingredient;
            }
        }

        return null;
    }

    /**
     * Get the ID of the Recipe
     *
     * @return the ID
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the Recipe (Used by Hibernate)
     *
     * @param id the ID
     */
    @SuppressWarnings("unused")
    private void setId(final Long id) {
        this.id = id;
    }

    /**
     * Returns name of the recipe.
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the recipe name.
     *
     * @param name The name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the price of the recipe.
     *
     * @return Returns the price.
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Sets the recipe price.
     *
     * @param price The price to set.
     */
    public void setPrice(final Integer price) {
        this.price = price;
    }

    /**
     * Updates the fields to be equal to the passed Recipe
     *
     * @param r with updated fields
     */
    public void updateRecipe(final Recipe r) {
        setPrice(r.getPrice());

        for (Ingredient ingredient : r.getIngredients()) {
            if (this.getIngredient(ingredient.getIngredient()) != null) {
                this.getIngredient(ingredient.getIngredient()).setAmount(ingredient.getAmount());
            } else {
                Ingredient newIngredient = new Ingredient(ingredient.getIngredient(), ingredient.getAmount());
                this.addIngredient(newIngredient);
            }
        }

    }

    /**
     * Returns the name of the recipe.
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.name).append(", Ingredients: {");
        for (int i = 0; i < this.ingredients.size(); i++) {

            builder.append(this.ingredients.get(i).getIngredient().toString()).append(": ").append(this.ingredients.get(i).getAmount());

            if (i != this.ingredients.size() - 1) {
                builder.append(", ");
            }
        }

        builder.append("}");

        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        Integer result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
