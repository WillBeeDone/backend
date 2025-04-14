package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.repository.CategoryRepository;
import de.willbeedone.backend.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getAllCategoriesNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public boolean existsByName(String categoryName) {
        return categoryRepository.existsByName(categoryName);
    }
}
