package me.shulinina.web38.service;
import me.shulinina.web38.model.Ingredient;
public interface IngredientService {
    long addIngredient(Ingredient ingredient);
    default Ingredient getIngredient(long id) {
        return null;
    }
    Ingredient getAllIngredient();
    Ingredient editIngredient(long id, Ingredient ingredient);
    boolean deleteIngredient(long id);
    void deleteAllIngredient();
}