package me.shulinina.web38.model;
import java.util.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe  {
    private String recipeName;
    private int recipeNum;
    private String cookingTime;
    private Set ingredients = new HashSet<Map<String, Ingredients>>();
    private Set cookingSteps = new HashSet<String>();
}