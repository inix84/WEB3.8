package me.shulinina.web38.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и др. эндпоинты для работы с рецептами")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping
    @Operation(
            summary = "Поиск рецептов по названию или номеру",
            description = "Можно искать по названию и/или номеру рецепта или без параметров"
    )
    @Parameters(value = {
            @Parameter(name = "recipe", example = "Сырники")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )

                    }
            )
    })
    public ResponseEntity<Recipe> getAllRecipes(@RequestParam(required = false) Recipe recipeName,
                                                @RequestParam(required = false) Recipe recipeNum) {
        return null;
    }
    @GetMapping("/{recipeNum}")
    @Operation(
            summary = "Поиск рецептов по номеру",
            description = "Можно искать по номеру рецепта"
    )
    @Parameters(value = {
            @Parameter(name = "recipeNum", example = "3")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> getRecipeByNum(@PathVariable(required = true) int recipeNum) {
        Recipe recipeByNum = recipeService.getRecipeByNum(recipeNum);
        if (recipeByNum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeByNum);
    }
    @PostMapping
    @Operation(
            summary = "Создание рецепта"
    )
    @Parameters(value = {
            @Parameter(name = "recipeName", example = "Сырники"),
            @Parameter(name = "recipeNum", example = "2"),
            @Parameter(name = "cookingTime", example = "30 минут"),
            @Parameter(name = "ingredients", example = "Творог 200г, яйцо 3 шт, сахар по вкусу"),
            @Parameter(name = "cookingSteps", example = "Перемешать все ингредиенты до однородной массы")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт создан",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok().body(newRecipe);
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addRecipesFromFile(@RequestParam MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            recipeService.addRecipesFromInputStream(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }
    @DeleteMapping("/{recipeNum}")
    @Operation(
            summary = "Удаление рецепта по номеру",
            description = "Нужно искать по номеру рецепта"
    )
    @Parameters(value = {
            @Parameter(name = "recipeNum", example = "3")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteRecipeByNum(@PathVariable int recipeNum) {
        if (recipeService.deleteRecipe(recipeNum)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{recipeNum}")
    @Operation(
            summary = "Редактирование рецепта по номеру",
            description = "Нужно искать по номеру рецепта"
    )
    @Parameters(value = {
            @Parameter(name = "recipeNum", example = "3")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            )
    })
    public ResponseEntity<Recipe> editRecipeByNum(@PathVariable int recipeNum, @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.editRecipe(recipeNum, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newRecipe);
    }
}