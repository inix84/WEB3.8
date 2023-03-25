package me.shulinina.web38.service;
import java.io.File;
public interface IngredientFileService {
    boolean saveToFileIng(String json);
    String readFromFileIng();
    File getDataFileIng();
    boolean cleanDataFileIng();
}