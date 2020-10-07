package advolang.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import advolang.app.models.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String>{
    public Category findByValue(String value);
}
