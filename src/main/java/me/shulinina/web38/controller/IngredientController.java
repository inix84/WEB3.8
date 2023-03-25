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
import me.shulinina.web38.model.Ingredients;
import me.shulinina.web38.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ингредиенты", description = "CRUD-операции и др. эндпоинты для работы с ингредиентами")
public class IngredientController {
    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping
    @Operation(
            summary = "Поиск ингредиентов по названию",
            description = "Можно искать по параметру или без"
    )
    @Parameters( value = {
            @Parameter(name = "ingredient", example = "Морковь")
    })
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredients> getAll(@RequestParam(required = false) Ingredients ingredient){
        return null;
    }
    @GetMapping("/{ingredient}")
    @Operation(
            summary = "Поиск ингредиента",
            description = "Нужно искать по названию"
    )
    @Parameters( value = {
            @Parameter(name = "ingredient", example = "Морковь")
    })
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public ResponseEntity<Ingredients> getIngredient(@PathVariable Ingredients ingredient){
        Ingredients neededIngredient = ingredientService.getIngredient(ingredient);
        if (neededIngredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( neededIngredient);
    }
    @PostMapping
    @Operation(
            summary = "Создание ингредиента"
    )
    @Parameters( value = {
            @Parameter(name = "ingredientName", example = "Морковь"),
            @Parameter(name = "measureUnit", example = "2 шт")
    })
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент создан",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> createIngredient(@RequestBody String ingredient, String measureUnit) {
        String newIngredient = ingredientService.addIngredient(ingredient,measureUnit);
        return ResponseEntity.ok().body(newIngredient);
    }
    @DeleteMapping("/{ingredient}")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Нужно искать по названию"
    )
    @Parameters( value = {
            @Parameter(name = "ingredient", example = "Морковь")
    })
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public ResponseEntity<Void> deleteIngredient(@PathVariable Ingredients ingredient) {
        if (ingredientService.deleteIngredient(ingredient)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{ingredient}")
    @Operation(
            summary = "Редактирование ингредиента",
            description = "Нужно искать по насванию ингредиента"
    )
    @Parameters( value = {
            @Parameter(name = "ingredient", example = "Морковь")
    })
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json" ,
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })
    public ResponseEntity<String> editIngredient(@PathVariable String ingredient, @PathVariable String measureUnit, @RequestBody Ingredients ingredients) {
        String newIngredient = ingredientService.editIngredient(ingredient, measureUnit);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newIngredient);
    }
}
