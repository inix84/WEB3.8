package me.shulinina.web38.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shulinina.web38.model.Recipe;
import me.shulinina.web38.service.RecipeFilesService;
import me.shulinina.web38.service.RecipeService;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    final private RecipeFilesService recipeFilesService;
    private static Map<Long, Recipe> recipes = new TreeMap<>();
    private  static  long lastId = 0;
    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }
    @PostConstruct
    private void init() {
        try {
            readFrommFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public long addRecipe(Recipe recipe) {
        recipes.put(lastId, recipe);
        saveToFile();
        return lastId++;
    }
    @Override
    public Recipe getRecipe(long id) {
        for (Recipe r : recipes.values()) {
            Recipe recipe = recipes.get(id);
            if (recipe != null) {
                return recipe;
            }
        }
        return null;
    }
    @Override
    public Recipe editRecipe(long id, Recipe recipe) {
        for (Recipe r : recipes.values()) {
            if (recipes.containsKey(id)) {
                recipes.put(id, recipe);
                saveToFile();
                return recipe;
            }
        }
        return null;
    }
    @Override
    public boolean deleteRecipe(long id) {
        for (Recipe r : recipes.values()) {
            if (recipes.containsKey(id)) {
                recipes.remove(id);
                return true;
            }
        }
        return false;
    }
    @Override
    public void deleteAllRecipe() {
        recipes = new TreeMap<>();
    }
    @Override
    public Recipe getAllRecipe() {
        return (Recipe) recipes;
    }
    //скачивание рецептов
    @Override
    public void addRecipesFromInputStream(InputStream inputStream) throws IOException {
    }
    //запись в файл
    private void saveToFile() {
        try {
            DataFile dataFile = new DataFile();
            String json = new ObjectMapper().writeValueAsString(dataFile);//сохранение рецепта в файл
            recipeFilesService.saveRecipesToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось сохранить рецепт в файл");
        }
    }
    //чтение из файла
    private void readFrommFile() {
        try {
            String json = recipeFilesService.readRecipesFromFile();

            DataFile dataFile= new ObjectMapper().readValue(json, new TypeReference<DataFile>() {
            });
            lastId = dataFile.getLastId();
            recipes = dataFile.getRecipes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Не удалось получить рецепт из файла");
        }
    }
    @Data
    @NoArgsConstructor
    private static class DataFile{
        private long lastId;
        private TreeMap<Long, Recipe>recipes;
    }
}