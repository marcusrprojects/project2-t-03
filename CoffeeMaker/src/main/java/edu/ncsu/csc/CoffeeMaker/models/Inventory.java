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
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /**
     * id for inventory entry
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * list of Ingredients in inventory
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Ingredient> ingredients;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory() {
        this.ingredients = new ArrayList<>();
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
    }

    /**
     * Use this to create inventory with specified amts.
     *
     * @param coffee    amt of coffee
     * @param milk      amt of milk
     * @param sugar     amt of sugar
     * @param chocolate amt of chocolate
     */
    public Inventory(final Integer coffee, final Integer milk, final Integer sugar, final Integer chocolate) {
        this.ingredients = new ArrayList<>();
        setIngredient(IngredientType.COFFEE, coffee);
        setIngredient(IngredientType.MILK, milk);
        setIngredient(IngredientType.SUGAR, sugar);
        setIngredient(IngredientType.CHOCOLATE, chocolate);
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id the ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Returns the current number of units of the requested ingredient in the inventory
     *
     * @param ingredientType the type of ingredient to fetch
     * @return amount of the requested ingredient
     */
    public Integer getIngredient(IngredientType ingredientType) {
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.getIngredient() == ingredientType) {
                return ingredient.getAmount();
            }
        }

        return 0;
    }

    /**
     * Sets the number of units of the requested ingredient in the inventory to the specified amount.
     *
     * @param ingredientType the type of the ingredient to set
     * @param amt amount of ingredient to set
     */
    public void setIngredient(IngredientType ingredientType, Integer amt) {
        for (Ingredient ingredient : this.ingredients) {
            if (ingredient.getIngredient() == ingredientType) {
                ingredient.setAmount(amt);
                return;
            }
        }

        this.ingredients.add(new Ingredient(ingredientType, amt));
    }

    /**
     * Add the number of units of the requested ingredient in the inventory to the current amount
     * of units of that ingredient.
     *
     * @param ingredientType ingredient to add to
     * @param ingredient amount to add
     * @return checked amount of ingredient
     * @throws IllegalArgumentException if the parameter isn't a positive integer or the Ingredient
     * isn't in the inventory
     */
    public Integer checkIngredient(IngredientType ingredientType, final String ingredient) {
        Integer amtIngredient = 0;
        try {
            amtIngredient = Integer.parseInt(ingredient);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("Units of ingredient must be a positive integer");
        }
        if (amtIngredient < 0) {
            throw new IllegalArgumentException("Units of ingredient must be a positive integer");
        }

        return amtIngredient;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients(final Recipe r) {
        boolean isEnough = true;

        for (Ingredient ingredient : r.getIngredients()) {
            if (getIngredient(ingredient.getIngredient()) < ingredient.getAmount()) {
                isEnough = false;
            }
        }

        return isEnough;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients(final Recipe r) {
        if (enoughIngredients(r)) {
            for (Ingredient ingredient : r.getIngredients()) {
                if (getIngredient(ingredient.getIngredient()) != null) {
                    setIngredient(ingredient.getIngredient(), getIngredient(ingredient.getIngredient()) - ingredient.getAmount());
                } else {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds ingredients to the inventory
     *
     * @param coffee    amt of coffee
     * @param milk      amt of milk
     * @param sugar     amt of sugar
     * @param chocolate amt of chocolate
     * @return true if successful, false if not
     */
    public boolean addIngredients(final Integer coffee, final Integer milk, final Integer sugar,
                                  final Integer chocolate) {
        if (coffee < 0 || milk < 0 || sugar < 0 || chocolate < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        setIngredient(IngredientType.COFFEE, getIngredient(IngredientType.COFFEE) + coffee);
        setIngredient(IngredientType.MILK, getIngredient(IngredientType.MILK) + milk);
        setIngredient(IngredientType.SUGAR, getIngredient(IngredientType.SUGAR) + sugar);
        setIngredient(IngredientType.CHOCOLATE, getIngredient(IngredientType.CHOCOLATE) + chocolate);

        return true;
    }

    /**
     * Returns a string describing the current contents of the inventory.
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("Coffee: ");
        buf.append(getIngredient(IngredientType.COFFEE));
        buf.append("\n");
        buf.append("Milk: ");
        buf.append(getIngredient(IngredientType.MILK));
        buf.append("\n");
        buf.append("Sugar: ");
        buf.append(getIngredient(IngredientType.SUGAR));
        buf.append("\n");
        buf.append("Chocolate: ");
        buf.append(getIngredient(IngredientType.CHOCOLATE));
        buf.append("\n");
        return buf.toString();
    }

}
