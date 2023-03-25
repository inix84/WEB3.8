package me.shulinina.web38.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.shulinina.web38.model.Ingredients;
import me.shulinina.web38.service.IngredientFileService;
import me.shulinina.web38.service.IngredientService;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.TreeMap;
@Service
public class IngredientServiceImpl implements IngredientService {
    final private IngredientFileService ingredientFileService;
    private static TreeMap<String, String> ingredientsMap = new TreeMap<String, String>();
    public IngredientServiceImpl(IngredientFileService ingredientFileService) {
        this.ingredientFileService = ingredientFileService;
    }
    @PostConstruct
    private void initIng(){
        readFromFileIng();
    }
    @Override
    public String addIngredient(String ingredient, String measureUnit) {
        if (ingredientsMap.containsKey(ingredient)) {
            return null;
        } else {
            ingredientsMap.put(ingredient, measureUnit);
            saveToFileIng();
            return ingredient;
        }
    }
    @Override
    public Ingredients getIngredient(Ingredients ingredient) {
        if (ingredientsMap.containsKey(ingredient)) {
            return ingredient;
        } else {
            return null;
        }
    }
    @Override
    public boolean deleteIngredient(Ingredients ingredient) {
        if (ingredientsMap.containsKey(ingredient)) {
            ingredientsMap.remove(ingredient);
            saveToFileIng();
            return true;
        }
        return false;
    }
    @Override
    public String editIngredient(String ingredient, String measureUnit) {
        if (ingredientsMap.containsKey(ingredient)) {
            ingredientsMap.put(ingredient, measureUnit);
            saveToFileIng();
            return ingredient;
        }
        return null;
    }
    @Override
    public TreeMap<String, String> getAll() {
        return ingredientsMap;
    }
    private void saveToFileIng(){
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            ingredientFileService.saveToFileIng(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFileIng(){
        String json = ingredientFileService.readFromFileIng();
        try {
            ingredientsMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<String, String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}