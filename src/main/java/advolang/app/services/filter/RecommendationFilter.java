package advolang.app.services.filter;

import java.util.List;

import advolang.app.models.Category;
import advolang.app.models.Recommendation;

/**
 * RecommendationFilter
 */
public interface RecommendationFilter {

    List<Recommendation> filter(List<Recommendation> recommendations, List<String> categories, String title,
            String difficulty);

	List<Recommendation> filterByCategories(List<Recommendation> allRecoms, List<Category> categories);

	List<Recommendation> filterBySubs(List<Recommendation> allRecoms, List<String> subs);
}