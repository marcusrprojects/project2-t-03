package edu.ncsu.csc.CoffeeMaker;

import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith (SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest (classes = TestConfig.class)
public class TestDatabaseInteraction {
    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    public void testRecipes(){
        recipeService.deleteAll();

        Recipe r = new Recipe();

        r.setPrice(350);
        r.setName("Mocha");
        r.setCoffee(2);
        r.setSugar(1);
        r.setMilk(1);
        r.setChocolate(1);

        recipeService.save(r);

        List<Recipe> dbRecipes = recipeService.findAll();

        Assertions.assertEquals(1, dbRecipes.size());

        Recipe dbRecipe = dbRecipes.get(0);

        Assertions.assertEquals(r.getName(), dbRecipe.getName());

        Assertions.assertEquals("Mocha", r.getName());
        Assertions.assertEquals(350, r.getPrice());
        Assertions.assertEquals(2, r.getCoffee());
        Assertions.assertEquals(1, r.getSugar());
        Assertions.assertEquals(1, r.getMilk());
        Assertions.assertEquals(1, r.getChocolate());

        dbRecipe.setPrice(15);
        dbRecipe.setSugar(12);
        recipeService.save(dbRecipe);


        Assertions.assertEquals(1, recipeService.count());

        Assertions.assertEquals(15, (int) recipeService.findAll().get(0).getPrice());
        Assertions.assertEquals(12, (int) recipeService.findAll().get(0).getSugar());
    }
}
