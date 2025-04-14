package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    List<String> getAllCategoriesNames();

    Category getCategoryByName(String name);

    boolean existsByName(String categoryName);

}
