package edu.ncsu.csc.CoffeeMaker.unit;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;
import org.junit.jupiter.api.Assertions;
import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(classes = TestConfig.class)
public class InventoryTest {

	@Autowired
	private InventoryService inventoryService;

	@BeforeEach
	public void setup() {
		final Inventory ivt = inventoryService.getInventory();

		ivt.setIngredient(IngredientType.CHOCOLATE, 500);
		ivt.setIngredient(IngredientType.COFFEE, 500);
		ivt.setIngredient(IngredientType.MILK, 500);
		ivt.setIngredient(IngredientType.SUGAR, 500);

		inventoryService.save(ivt);
	}

	@Test
	@Transactional
	public void testConsumeInventory() {
		final Inventory i = inventoryService.getInventory();

		final Recipe recipe = new Recipe();
		recipe.setName("Delicious Not-Coffee");
		recipe.addIngredient(new Ingredient(IngredientType.CHOCOLATE, 10));
		recipe.addIngredient(new Ingredient(IngredientType.MILK, 20));
		recipe.addIngredient(new Ingredient(IngredientType.SUGAR, 5));
		recipe.addIngredient(new Ingredient(IngredientType.COFFEE, 1));

		recipe.setPrice(5);

		i.useIngredients(recipe);

		/*
		 * Make sure that all of the inventory fields are now properly updated
		 */

		Assertions.assertEquals(490, (int) i.getIngredient(IngredientType.CHOCOLATE));
		Assertions.assertEquals(480, (int) i.getIngredient(IngredientType.MILK));
		Assertions.assertEquals(495, (int) i.getIngredient(IngredientType.SUGAR));
		Assertions.assertEquals(499, (int) i.getIngredient(IngredientType.COFFEE));
	}

	@Test
	@Transactional
	public void testAddInventory1() {
		Inventory ivt = inventoryService.getInventory();

		ivt.addIngredients(5, 3, 7, 2);

		/* Save and retrieve again to update with DB */
		inventoryService.save(ivt);

		ivt = inventoryService.getInventory();

		Assertions.assertEquals(505, (int) ivt.getIngredient(IngredientType.COFFEE),
				"Adding to the inventory should result in correctly-updated values for coffee");
		Assertions.assertEquals(503, (int) ivt.getIngredient(IngredientType.MILK),
				"Adding to the inventory should result in correctly-updated values for milk");
		Assertions.assertEquals(507, (int) ivt.getIngredient(IngredientType.SUGAR),
				"Adding to the inventory should result in correctly-updated values sugar");
		Assertions.assertEquals(502, (int) ivt.getIngredient(IngredientType.CHOCOLATE),
				"Adding to the inventory should result in correctly-updated values chocolate");

	}

	@Test
	@Transactional
	public void testAddInventory2() {
		final Inventory ivt = inventoryService.getInventory();

		try {
			ivt.addIngredients(-5, 3, 7, 2);
		} catch (final IllegalArgumentException iae) {
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.COFFEE),
					"Trying to update the Inventory with an invalid value for coffee should result in no changes -- coffee");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.MILK),
					"Trying to update the Inventory with an invalid value for coffee should result in no changes -- milk");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.SUGAR),
					"Trying to update the Inventory with an invalid value for coffee should result in no changes -- sugar");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.CHOCOLATE),
					"Trying to update the Inventory with an invalid value for coffee should result in no changes -- chocolate");
		}
	}

	@Test
	@Transactional
	public void testAddInventory3() {
		final Inventory ivt = inventoryService.getInventory();

		try {
			ivt.addIngredients(5, -3, 7, 2);
		} catch (final IllegalArgumentException iae) {
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.COFFEE),
					"Trying to update the Inventory with an invalid value for milk should result in no changes -- coffee");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.MILK),
					"Trying to update the Inventory with an invalid value for milk should result in no changes -- milk");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.SUGAR),
					"Trying to update the Inventory with an invalid value for milk should result in no changes -- sugar");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.CHOCOLATE),
					"Trying to update the Inventory with an invalid value for milk should result in no changes -- chocolate");

		}

	}

	@Test
	@Transactional
	public void testAddInventory4() {
		final Inventory ivt = inventoryService.getInventory();

		try {
			ivt.addIngredients(5, 3, -7, 2);
		} catch (final IllegalArgumentException iae) {
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.COFFEE),
					"Trying to update the Inventory with an invalid value for sugar should result in no changes -- coffee");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.MILK),
					"Trying to update the Inventory with an invalid value for sugar should result in no changes -- milk");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.SUGAR),
					"Trying to update the Inventory with an invalid value for sugar should result in no changes -- sugar");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.CHOCOLATE),
					"Trying to update the Inventory with an invalid value for sugar should result in no changes -- chocolate");

		}

	}
  
    @Test
    @Transactional
    public void testChecks () {
    	
    	final Inventory ivt = inventoryService.getInventory();
    	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.CHOCOLATE, "-1");
		}, "Cannot add negative amount of chocolate");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.CHOCOLATE, "negative one");
		}, "Cannot add a string that does not parse into an integer");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.MILK, "-1");
		}, "Cannot add negative amount of milk");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.MILK, "negative one");
		}, "Cannot add a string that does not parse into an integer");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.SUGAR, "-1");
		}, "Cannot add negative amount of sugar");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.SUGAR, "negative one");
		}, "Cannot add a string that does not parse into an integer");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.COFFEE, "-1");
		}, "Cannot add negative amount of chocolate");
	
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ivt.checkIngredient(IngredientType.COFFEE, "negative one");
		}, "Cannot add a string that does not parse into an integer");
	}

	@Test
	@Transactional
	public void testAddInventory5() {
		final Inventory ivt = inventoryService.getInventory();

		try {
			ivt.addIngredients(5, 3, 7, -2);
		} catch (final IllegalArgumentException iae) {
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.COFFEE),
					"Trying to update the Inventory with an invalid value for chocolate should result in no changes -- coffee");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.MILK),
					"Trying to update the Inventory with an invalid value for chocolate should result in no changes -- milk");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.SUGAR),
					"Trying to update the Inventory with an invalid value for chocolate should result in no changes -- sugar");
			Assertions.assertEquals(500, (int) ivt.getIngredient(IngredientType.CHOCOLATE),
					"Trying to update the Inventory with an invalid value for chocolate should result in no changes -- chocolate");

		}

	}

	@Test
	@Transactional
	public void testCheckInventory() {
		final Inventory ivt = inventoryService.getInventory();
		ivt.addIngredients(5, 3, 7, 2);
		ivt.setId((long) (2));
		Assertions.assertEquals((long) (2), (long) ivt.getId());
		// Chocolate
		try {
			ivt.setIngredient(IngredientType.CHOCOLATE, ivt.checkIngredient(IngredientType.CHOCOLATE, "-5"));
			Assertions.fail("should fail as cannot set to a negative int");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.CHOCOLATE, ivt.checkIngredient(IngredientType.CHOCOLATE, "5"));
		}
		try {
			ivt.setIngredient(IngredientType.CHOCOLATE, ivt.checkIngredient(IngredientType.CHOCOLATE, "five"));
			Assertions.fail("should fail as cannot set to a string");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.CHOCOLATE, ivt.checkIngredient(IngredientType.CHOCOLATE, "5"));
		}
		Assertions.assertEquals(5, (int) ivt.getIngredient(IngredientType.CHOCOLATE));
		// Coffee
		try {
			ivt.setIngredient(IngredientType.COFFEE, ivt.checkIngredient(IngredientType.COFFEE, "-5"));
			Assertions.fail("should fail as cannot set to a negative int");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.COFFEE, ivt.checkIngredient(IngredientType.COFFEE, "5"));
		}
		try {
			ivt.setIngredient(IngredientType.COFFEE, ivt.checkIngredient(IngredientType.COFFEE, "five"));
			Assertions.fail("should fail as cannot set to a string");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.COFFEE, ivt.checkIngredient(IngredientType.COFFEE, "5"));
		}
		Assertions.assertEquals(5, (int) ivt.getIngredient(IngredientType.COFFEE));
		// Milk
		try {
			ivt.setIngredient(IngredientType.MILK, ivt.checkIngredient(IngredientType.MILK, "-5"));
			Assertions.fail("should fail as cannot set to a negative int");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.MILK, ivt.checkIngredient(IngredientType.MILK, "5"));
		}
		try {
			ivt.setIngredient(IngredientType.MILK, ivt.checkIngredient(IngredientType.MILK, "five"));
			Assertions.fail("should fail as cannot set to a string");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.MILK, ivt.checkIngredient(IngredientType.MILK, "5"));
		}
		Assertions.assertEquals(5, (int) ivt.getIngredient(IngredientType.MILK));
		// Sugar
		try {
			ivt.setIngredient(IngredientType.SUGAR, ivt.checkIngredient(IngredientType.SUGAR, "-5"));
			Assertions.fail("should fail as cannot set to a negative int");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.SUGAR, ivt.checkIngredient(IngredientType.SUGAR, "5"));
		}
		try {
			ivt.setIngredient(IngredientType.SUGAR, ivt.checkIngredient(IngredientType.SUGAR, "five"));
			Assertions.fail("should fail as cannot set to a string");
		} catch (IllegalArgumentException e) {
			ivt.setIngredient(IngredientType.SUGAR, ivt.checkIngredient(IngredientType.SUGAR, "5"));
		}
		Assertions.assertEquals(5, (int) ivt.getIngredient(IngredientType.SUGAR));
	}
	
	@Test
	@Transactional
	public void testToString() {
		final Inventory ivt = inventoryService.getInventory();
		ivt.addIngredients(5, 3, 7, 2);
		Assertions.assertEquals("Coffee: 505\n"
				+ "Milk: 503\n"
				+ "Sugar: 507\n"
				+ "Chocolate: 502\n",  ivt.toString());
		
	}
	
	@Test
	@Transactional
	public void testEnoughIngredients() {
		final Inventory ivt = inventoryService.getInventory();
		ivt.addIngredients(5, 3, 7, 2);
        final Recipe r1 = new Recipe();
        r1.setName( "test" );
        r1.setPrice( 1000 );
		r1.addIngredient(new Ingredient(IngredientType.COFFEE, 1000));
		r1.addIngredient(new Ingredient(IngredientType.MILK, 1000));
		r1.addIngredient(new Ingredient(IngredientType.SUGAR, 1000));
		r1.addIngredient(new Ingredient(IngredientType.CHOCOLATE, 1000));
        Assertions.assertEquals(false, ivt.enoughIngredients(r1));
		
	}

}
