package me.shulinina.web38.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.shulinina.web38.model.Recipe;
import me.shulinina.web38.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
@RestController
@RequestMapping("/recipes")
@Tag(name = "Рецепты", description = "CRUD-операции  для работы с рецептами" )
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    //Добавление нового рецепта
    @PostMapping
    @Operation(summary = "Добавление рецепта")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> addRecipe(@RequestBody Recipe recipe) {
        long id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok(id);
    }
    //Получение рецепта по id
    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск рецепта по id",
            description = "Для получения рецепта введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getRecipeById(@PathVariable long id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    //Получение списка всех рецептов
    @GetMapping
    @Operation( summary = "Получение списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getAllRecipe() {
        recipeService.getAllRecipe();
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addReciresFromFile(@RequestParam MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            recipeService.addRecipesFromInputStream(stream);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
    //Редактирование рецепта по id
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление сведений по рецепту",
            description = "Для редактирования рецепта введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> editRecipe(@PathVariable long id, @RequestBody Recipe recipe) {
        Recipe r = recipeService.editRecipe(id, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    //Удаление рецепта по id
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта по id",
            description = "Для удаления рецепта введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт был удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    //Удаление всех рецептов
    @DeleteMapping
    @Operation(summary = "Удаление всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты были удалены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteAllRecipe() {
        recipeService.deleteAllRecipe();
        return ResponseEntity.ok().build();
    }
}