package me.shulinina.web38.service;
import java.io.File;
public interface RecipeFilesService {
    boolean saveToFile(String json);
    String readFromFile();
    File getDataFile();
    boolean cleanDataFile();
}