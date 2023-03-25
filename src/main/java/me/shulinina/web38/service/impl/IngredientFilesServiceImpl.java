package me.shulinina.web38.service.impl;
import me.shulinina.web38.service.IngredientFilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class IngredientFilesServiceImpl implements IngredientFilesService {
    @Value("${path.to.ingredients.file}")
    private String ingredientsFilePath;
    @Value("${name.of.ingredients.file}")
    private String ingredientsFileName;
    //запись файла
    @Override
    public boolean saveIngredientsToFile(String json){
        try {
            cleanIngredientsFile();
            Files.writeString(Path.of(ingredientsFilePath, ingredientsFileName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //чтение из файла
    @Override
    public String readIngredientsFromFile(){
        try {
            return Files.readString(Path.of(ingredientsFilePath, ingredientsFileName));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось получить ингредиент из файла");
        }
    }
    //метод возвращает файл
    @Override
    public File getFile(){
        return new File(ingredientsFilePath + "/" + ingredientsFileName);
    }
    //создание временных файлов
    @Override
    public Path createTempFile(String suffix){
        try {
            return  Files.createTempFile(Path.of(ingredientsFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать временный файл");
        }
    }
    //удалить и очистить файл
    public boolean cleanIngredientsFile(){
        try {
            Path path = Path.of(ingredientsFilePath, ingredientsFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}