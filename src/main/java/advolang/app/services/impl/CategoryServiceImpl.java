package advolang.app.services.impl;

import advolang.app.models.Category;
import advolang.app.repository.CategoryRepository;
import advolang.app.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<String> getListCategory() {
        List<String> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            categories.add(category.getValue());
        });
        return categories;
    }
}