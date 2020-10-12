package advolang.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import advolang.app.models.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String>{
    Category findByValue(String value);

    Boolean existsByValue(String value);
}
