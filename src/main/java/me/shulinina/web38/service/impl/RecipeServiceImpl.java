package me.shulinina.web38.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shulinina.web38.model.Recipe;
import me.shulinina.web38.service.RecipeFilesService;
import me.shulinina.web38.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.*;
import java.util.TreeMap;
@Service
public class RecipeServiceImpl implements RecipeService {
    final private RecipeFilesService recipeFilesService;
    private static TreeMap<Integer, Recipe> recipesMap = new TreeMap<Integer, Recipe>();

    int counter = 0;
    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }
    @PostConstruct
    private void init() {
        readFromFile();
    }
    @Override
    public Recipe getRecipeByNum(int recipeNum) {
        if (recipesMap.containsKey(recipeNum)) {
            return recipesMap.get(recipeNum);
        } else {
            return null;
        }
    }
    @Override
    public void addRecipesFromInputStream(MultipartFile file) throws IOException {
        String content = new String(file.getBytes());
        objectMapper.convertValue(content, new TypeReference<TreeMap<Integer, Recipe>>() {
        });
        saveToFile();
    }
    @Override
    public Recipe createRecipe(Recipe recipe) {
        if (recipesMap.containsKey(recipe.getRecipeNum())) {
            return null;
        } else {
            recipesMap.put(recipe.getRecipeNum(), recipe);
            saveToFile();
            counter++;
            return recipe;
        }
    }
    @Override
    public boolean deleteRecipe(int recipeNum) {
        if (recipesMap.containsKey(recipeNum)) {
            recipesMap.remove(recipeNum);
            saveToFile();
            return true;
        }
        return false;
    }
    @Override
    public Recipe editRecipe(int recipeNum, Recipe recipeName) {
        if (recipesMap.containsKey(recipeNum)) {
            recipesMap.put(recipeNum, recipeName);
            saveToFile();
            return recipeName;
        }
        return null;
    }
    @Override
    public TreeMap<Integer, Recipe> getAll() {
        return recipesMap;
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipesMap);
            recipeFilesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
        String json = recipeFilesService.readFromFile();
        try {
            recipesMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}