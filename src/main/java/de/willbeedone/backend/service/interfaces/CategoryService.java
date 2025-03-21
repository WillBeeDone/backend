package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    List<String> getAllCategoriesNames();

    Category getCategoryById(Long id);

    Category addNewCategory(Category category);

    void deleteCategory(Long id);

}
