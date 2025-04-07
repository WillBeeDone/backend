package de.willbeedone.backend.controller;

import de.willbeedone.backend.service.interfaces.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category controller", description = "Controller for various operations with Offers' categories.")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Getting all categoryDto names",
            description = "Returns the list of all categoryDto names (e.g. for dropdown on the Home Page.")
    @GetMapping
    public List<String> getAllCategoriesNames() {
        return categoryService.getAllCategoriesNames();
    }

}
