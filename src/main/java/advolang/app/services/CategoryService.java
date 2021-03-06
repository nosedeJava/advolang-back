package advolang.app.services;

import advolang.app.models.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category) throws Exception;

    List<Category> getAllCategories();
}
