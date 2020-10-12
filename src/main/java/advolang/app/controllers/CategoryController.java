package advolang.app.controllers;

import advolang.app.exceptions.UserBadRequest;
import advolang.app.models.Category;
import advolang.app.repository.CategoryRepository;
import advolang.app.services.impl.CategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("*")
@RequestMapping("/api")
public class CategoryController {

    final CategoryRepository categoryRepository;
    final CategoryServiceImpl categoryService;

    public CategoryController(CategoryRepository categoryRepository, CategoryServiceImpl categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    /**
     *
     * @param category new category
     * @return A response with check status
     */
    @PostMapping("/category")
    public ResponseEntity<?> creteCategory(@RequestBody Category category){
        if (categoryRepository.existsByValue(category.getValue())){
            return new ResponseEntity<>("The category already exists", HttpStatus.FOUND);
        }
        try{
            this.categoryService.createCategory(category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new UserBadRequest("Error in post"), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/categoryList")
    public ResponseEntity<?> getCategoryList(){
        try {
            return new ResponseEntity<>(categoryService.getListCategory(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
