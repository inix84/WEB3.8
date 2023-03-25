package me.shulinina.web38.service;
import java.io.File;
import java.nio.file.Path;
public interface IngredientFilesService {
    boolean saveIngredientsToFile(String json);
    String readIngredientsFromFile();
    Path createTempFile(String suffix);
    boolean cleanIngredientsFile();
    File getFile();
}