package me.shulinina.web38.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shulinina.web38.model.Recipe;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.TreeMap;
public abstract interface RecipeService {
    Recipe getRecipeByNum(int recipeNum);
    void addRecipesFromInputStream(MultipartFile file) throws IOException;
    Recipe createRecipe(Recipe recipe);
    boolean deleteRecipe(int recipeNum);
    Recipe editRecipe(int recipeNum, Recipe recipeName);
    TreeMap<Integer, Recipe> getAll();
    TreeMap<Integer, Recipe> recipesMap = new TreeMap<>();
    ObjectMapper objectMapper = new ObjectMapper();
}