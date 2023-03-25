package me.shulinina.web38.service.impl;
import me.shulinina.web38.service.IngredientFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class FileServiceImplIngredient implements IngredientFileService {
    @Value("${path.to.ingredient.data.file}")
    private String ingDataFilePath;
    @Value("${name.of.ingredient.data.file}")
    private String ingDataFileName;
    @Override
    public boolean saveToFileIng(String json) {
        try {
            cleanDataFileIng();
            Files.writeString(Path.of(ingDataFilePath, ingDataFileName),json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public String readFromFileIng() {
        try {
            return Files.readString(Path.of(ingDataFilePath, ingDataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public File getDataFileIng(){
        return new File(ingDataFilePath+"/"+ingDataFileName);
    }
    @Override
    public boolean cleanDataFileIng() {
        try {
            Path path = Path.of(ingDataFilePath, ingDataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}