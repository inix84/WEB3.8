package me.shulinina.web38.service;
import me.shulinina.web38.model.Recipe;
import java.io.IOException;
import java.io.InputStream;
public interface RecipeService {
    long addRecipe(Recipe recipe);
    Recipe getRecipe(long id);
    Recipe editRecipe(long id, Recipe recipe);
    boolean deleteRecipe(long id);
    void deleteAllRecipe();
    Recipe getAllRecipe();
    void addRecipesFromInputStream(InputStream inputStream) throws IOException;
}