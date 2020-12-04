package advolang.app.services.filter.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import advolang.app.models.Category;
import advolang.app.models.Recommendation;
import advolang.app.services.filter.RecommendationFilter;

@Service
public class RecommendationFilterImpl implements RecommendationFilter {

    @Override
    public List<Recommendation> filter(List<Recommendation> recommendations, List<String> categories, String title,
            String difficulty) {
        List<Recommendation> filteredRecommendations = new ArrayList<Recommendation>();
        for (Recommendation recommendation : recommendations) {
            if (difficulty != null && !difficulty.equals("Any") && !recommendation.getLevel().equals(difficulty)){
                continue;
            }
            if (title != null && !recommendation.getTitle().contains(title)){
                continue;
            }
            boolean haveAllCategories = true;
            List<String> recommendationCategories = new ArrayList<String>();
            for (Category category : recommendation.getCategories()) {
                recommendationCategories.add(category.getValue());
            }
            System.out.println(categories);
            if (categories == null){
                categories = new ArrayList<String>();
            }
            System.out.println(categories);
            for (String category : categories) {
                if (!recommendationCategories.contains(category)){
                    haveAllCategories = haveAllCategories && false;
                }
            }
            if (!haveAllCategories){
                continue;
            }
            filteredRecommendations.add(recommendation);
        }
        return filteredRecommendations;
    }
    
    @Override
    @SuppressWarnings("unlikely-arg-type")
	public List<Recommendation> filterByCategories(List<Recommendation> allRecoms, List<Category> categories){
    	
    	List<Recommendation> recoms = new ArrayList<Recommendation>();
    	for(Category c : categories) {
    		String catValue = c.getValue().toLowerCase();
    		for(Recommendation r : allRecoms) {
    			
    			List<Category> cats = r.getCategories();
    			
    			if (cats.contains(catValue) && !recoms.contains(r)) {
    				recoms.add(r);
    			}
    		}
    	}
    	
    	return recoms;
    }
    
	@Override
    public List<Recommendation> filterBySubs(List<Recommendation> allRecoms, List<String> subs){
    	
    	List<Recommendation> recoms = new ArrayList<Recommendation>();
    	for(String s : subs) {
    		for(Recommendation r : allRecoms) {
    			if (r.getLanguage().equals(s) && !recoms.contains(r)) {
    				recoms.add(r);
    			}
    		}
    	}
    	
    	return recoms;
    }

}
