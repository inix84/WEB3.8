package me.shulinina.web38.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.shulinina.web38.model.Ingredient;
import me.shulinina.web38.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции  для работы с ингредиентами" )
public class IngredientController {
    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    //Добавление нового ингредиента
    @PostMapping
    @Operation(summary = "Добавление ингредиента")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Long> addIngredient(@RequestBody Ingredient ingredient) {
        long id = ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(id);
    }
    //Получение ингредиента по id
    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск ингредиента по id",
            description = "Для получения ингредиента введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был найден",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> getIngredientById(@PathVariable long id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ингредиент не найден. Ошибка 404");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(ingredient.toString());
    }
    //Получение списка всех ингредиентов
    @GetMapping(value = "/allIngredients")
    @Operation( summary = "Получение списка всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты  были найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> getAllIngredient() {
        ingredientService.getAllIngredient();
        return ResponseEntity.ok().build();
    }
    //Редактирование ингредиента по id
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление сведений по ингредиенту",
            description = "Для редактирования ингредиента введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был изменен",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredient> editIngredient(@PathVariable long id, @RequestBody Ingredient ingredient) {
        Ingredient n = ingredientService.editIngredient(id, ingredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
    //Удаление ингредиента по id
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по id",
            description = "Для удаления ингредиента введите его id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент был удален",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) {
        if (ingredientService.deleteIngredient(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    //Удаление всех ингредиента
    @DeleteMapping
    @Operation(summary = "Удаление всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты были удалены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteAllIngredient() {
        ingredientService.deleteAllIngredient();
        return ResponseEntity.ok().build();
    }
}