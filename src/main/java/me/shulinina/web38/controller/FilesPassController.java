package me.shulinina.web38.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.shulinina.web38.service.IngredientFilesService;
import me.shulinina.web38.service.RecipeFilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Отправка  и загрузка файлов",
        description = " Операции по работе с файлами: отправка, загрузка, сохранение.")
public class FilesPassController {
    private final RecipeFilesService recipeFilesService;
    private final IngredientFilesService ingredientFilesService;
    public FilesPassController(RecipeFilesService recipeFilesService, IngredientFilesService ingredientFilesService) {
        this.recipeFilesService = recipeFilesService;
        this.ingredientFilesService = ingredientFilesService;
    }
    @GetMapping(value = "/recipeExport")
    @Operation(summary = "Сохранение файла с рецептами пользователем")
    public ResponseEntity<InputStreamResource> dowloadFile() throws FileNotFoundException {
        File file = recipeFilesService.getFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping(value = "/recipeImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с рецептами с компьютера пользователя")
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        recipeFilesService.cleanRecipesFile();
        File dataFile = recipeFilesService.getFile();
        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(dataFile)) {
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    //загрузить файл с ингредиентами на сервер
    @PostMapping(value = "/ingredientImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с ингредиентами с компьютера пользователя")
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        ingredientFilesService.cleanIngredientsFile();
        File dataFile = ingredientFilesService.getFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}