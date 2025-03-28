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

    @Operation(summary = "Getting all category names",
            description = "Returns the list of all category names (e.g. for dropdown on the Home Page.")
    @GetMapping
    public List<String> getAllCategoriesNames() {
        return categoryService.getAllCategoriesNames();
    }

//    @GetMapping("/{id}")
//    public Category getCategoryById(@PathVariable Long id) {
//        return categoryService.getCategoryById(id);
//    }
//
//    //@PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/new")
//    public Category addNewCategory(@RequestBody Category category) {
//        return categoryService.addNewCategory(category);
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
//        return categoryService.updateCategory(id, category);
//    }
//
//    //@PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable Long id) {
//        categoryService.deleteCategory(id);
//    }



}
