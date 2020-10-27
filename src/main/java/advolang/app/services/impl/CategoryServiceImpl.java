package advolang.app.services.impl;

import advolang.app.models.Category;
import advolang.app.repository.CategoryRepository;
import advolang.app.services.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(Category category) throws Exception {
        if (categoryRepository.existsByValue(category.getValue())){
            throw new Exception("Category already exists");
        }
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
