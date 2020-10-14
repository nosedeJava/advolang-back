package advolang.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import advolang.app.models.Category;


@Repository
public interface CategoryRepository extends MongoRepository<Category,String>{
    Optional<Category> findByValue(String value);

    Boolean existsByValue(String value);
}
