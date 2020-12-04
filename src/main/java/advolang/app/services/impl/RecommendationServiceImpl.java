package advolang.app.services.impl;

import advolang.app.exceptions.RecommendationNotFound;
import advolang.app.exceptions.UserBadRequest;
import advolang.app.exceptions.UserNotFound;
import advolang.app.models.Category;
import advolang.app.models.Recommendation;
import advolang.app.models.Score;
import advolang.app.models.User;
import advolang.app.repository.CategoryRepository;
import advolang.app.repository.RecomRepository;
import advolang.app.repository.ScoreRepository;
import advolang.app.repository.UserRepository;
import advolang.app.services.RecommendationService;
import advolang.app.services.UserService;
import advolang.app.services.filter.RecommendationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecomRepository recomRepository;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RecommendationFilter recommendationFilter;

    @Autowired
    private UserService userService;

    /**
     * When a recommendations is added, update to each category its popularity
     */

    @Override
    public void addRecommendation(Recommendation recommendation) throws RecommendationNotFound {
        try {
            this.recomRepository.save(recommendation);
            for (Category category : recommendation.getCategories()) {
                Category ct=catRepo.findByValue(category.getValue()).get();
                ct.setPopularity(ct.getPopularity()+1);
                catRepo.save(ct);
            }
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed to create a recommendations");
        }
    }

    @Override
    public List<Recommendation> getUserRecommendations(String creator) throws UserNotFound {
        try {
            return this.recomRepository.findByCreator(creator);
        } catch (Exception e) {
            throw new UserNotFound("No recommendations found for this user");
        }
    }
    
    @Override
	public List<Recommendation> getAllRecommendations() throws RecommendationNotFound {
    	try {
            return this.recomRepository.findAll();
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed find recommendations");
        }
	}

    @Override
    public List<Recommendation> getRecommendations(String language, List<String> values) throws RecommendationNotFound {
        try {
            List<Recommendation> recommendations = recomRepository.findByLanguage(language);
            return recommendations;
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed query on recommendations by language");
        }
    }

    @Override
    public List<Recommendation> getReportedRecommendations(String language) throws RecommendationNotFound {
        try {
            return recomRepository.findByReportedIsTrueAndLanguage(language);
        } catch (Exception e) {
            throw new RecommendationNotFound("Failed query on reported recommendations by language");
        }
    }

    @Override
    public Recommendation getSpecificRecommendationWithUser(String username, String id) throws RecommendationNotFound {
        try {
            return recomRepository.findByIdAndCreator(id, username).get();
        } catch (Exception e) {
            throw new RecommendationNotFound("Recommendation not found");
        }
    }
    
    @Override
    public Recommendation getSpecificRecommendation(String language, String id) throws RecommendationNotFound {
        try {
            return recomRepository.findById(id).get();
        } catch (Exception e) {
            throw new RecommendationNotFound("Recommendation not found");
        }
    }

    @Override
    public void addSubscription(String language, String username) throws UserNotFound {
        try {
            Optional<User> us= userRepository.findByUsername(username);
            if(!us.isPresent())throw new UserNotFound("No user exists with that username");
            User user = us.get();
            if(user.getSubscriptions().contains(language)) throw new UserNotFound("This user is already subscribed to this language");
            user.getSubscriptions().add(language);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotFound("Subscription failed");
        }
    }

    @Override
    public void removeSubscription(String language, String username) throws UserNotFound{
        try {
            Optional<User> us= userRepository.findByUsername(username);
            if(!us.isPresent())throw new UserNotFound("No user exists with that username");
            User user = us.get();
            if(!user.getSubscriptions().contains(language)) throw new UserNotFound("This user hasn't suscribed to this language");
            user.getSubscriptions().remove(language);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotFound("Remove subscription failed");
        }
    }

    @Override
    public Double getScoreOfRecommendation(String language, String recommendationId) throws RecommendationNotFound {
        // Confirmation of recommendation's existence
        if(!recomRepository.existsById(recommendationId)) throw new RecommendationNotFound("Error - Recommendation not found");
        // Calculation of the score
        List<Score> scores = scoreRepository.findAllByRecommendationId(recommendationId);
        float sum = 0;
        for(Score score : scores){
            sum+=score.getValue();
        }
        if(sum==0) return Double.valueOf("0");
        else {
            float average = sum / scores.size();
            Double result = Double.valueOf(average);
            return result;
        }
    }

    @Override
    public Double rateRecommendation(String language, String recommendationId, Score newScore) throws RecommendationNotFound, UserNotFound, Exception {
        // Confirmation of recommendation's existence and the consistent relationship
        if(!recomRepository.existsById(recommendationId)) throw new RecommendationNotFound("Error - Recommendation not found");
        if(!recommendationId.equals(newScore.getRecommendationId())) throw new Exception("Error - Inconsistent information");
        // Confirmation of user's existence
        if(!userRepository.existsById(newScore.getUserId())) throw new UserNotFound("Error - User not found");
        // Confirmation of score's existence
        Score previousScore = null;
        // If the user has previously scored
        try {
            previousScore = scoreRepository.findByUserAndRecommendation(newScore.getUserId(), newScore.getRecommendationId()).get();
            previousScore.setValue(newScore.getValue());
            scoreRepository.save(previousScore);
        }
        // In case the score is new
        catch(Exception e) {
            scoreRepository.save(newScore);
        }
        return getScoreOfRecommendation(language, recommendationId);
    }

    @Override
    public List<Recommendation> getFilterRecommendations(
                        List<String> categories, 
                        String title, 
                        String difficulty, 
                        String type, 
                        String language,
                        String username) throws UserBadRequest,Exception{
        List<Recommendation> recommendations;
        if(type.equals("saved")){
            recommendations=userService.getSavedRecommendations(username);
        }else if(type.equals("language") && language!=null){
            recommendations=recomRepository.findByLanguage(language);
        }else if(type.equals("principal")){
            recommendations=recomRepository.findAll();
        }else{
            throw new UserBadRequest("Bad request");
        }
        return recommendationFilter.filter(recommendations,categories,title,difficulty);
    }
    
    @Override
    public List<Recommendation> getTopRecommendations(int topNumber) throws RecommendationNotFound{
    	
    	List<Recommendation> recommendations = this.recomRepository.findAll();
    	List<Recommendation> topRecommendations = new ArrayList<Recommendation>();
    	
    	TreeMap<String, Recommendation> recomsDict = new TreeMap<String, Recommendation>();
    	TreeMap<String, Double> recomsRate = new TreeMap<String, Double>();

    	System.out.println(recommendations.size());
    	for(int i=0; i<recommendations.size();i++) {
    		
    		Recommendation recom = recommendations.get(i);
    		Double score = getScoreOfRecommendation(recom.getLanguage(), recom.getId());
    		
    		recomsDict.put(recom.getId(),recom);
    		recomsRate.put(recom.getId(), score);
    		
    	}
    	
    	Map sortedMap = sortByValues(recomsRate);
    	Set set = sortedMap.entrySet();
     
        Iterator i = set.iterator();
     
        int topListSize = 0;
        while(i.hasNext() && topListSize<=topNumber) {
          Map.Entry me = (Map.Entry)i.next();
          
          String recomId = (String) me.getKey();
          topRecommendations.add(recomsDict.get(recomId));
          topListSize++;
        
      }    	
    	return topRecommendations;
    }

	/**
	 * Descending order comparator
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	private <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {

		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
	
	@Override
	public List<Recommendation> filerRecomsForUser(String username) throws UserNotFound {
        User user = this.userRepository.findByUsername(username).get();
        
        List<String> subscriptions = user.getSubscriptions();
      
        List<Recommendation> recommendations = this.recomRepository.findAll();
        List<Recommendation> savedRecommendations = userService.getSavedRecommendations(username);
    	List<Recommendation> recommendedPosts = new ArrayList<Recommendation>();
    	
        for(Recommendation recom : savedRecommendations) {
        	removeRecomFromList(recommendations, recom);
        	
        	List<Category> categories = recom.getCategories();
        	addRecomToList(recommendationFilter.filterByCategories(recommendations, categories), recommendedPosts);

        	List<Recommendation> recomLanguage = this.recomRepository.findByLanguage(recom.getLanguage());
        	addRecomToList(recomLanguage, recommendedPosts);
        		
        	List<Recommendation> recomCreator = this.recomRepository.findByCreator(recom.getCreator());
        	addRecomToList(recomCreator, recommendedPosts);
        }
        
        addRecomToList(recommendationFilter.filterBySubs(recommendations, subscriptions), recommendedPosts);
        cleanRecomList(recommendedPosts, recommendations);
        
        return recommendedPosts;
	}
	
	/**
	 * Method to add list elements to another list
	 * @param list List with elements to add
	 * @param finalList Final list of elements
	 */
	private void addRecomToList(List<Recommendation> list, List<Recommendation> finalList) {
		for(Recommendation r:list) {
			if(!finalList.contains(r)) {
				finalList.add(r);
			}
		}
	}
    
	/**
	 * Method to remove recommendation from list
	 * @param list List to evaluate
	 * @param recom Recommendation to delete
	 */
	private void removeRecomFromList(List<Recommendation> list, Recommendation recom) {
		
		int i=0;
		boolean removed = false;
		
		while (i < list.size() && !removed) {
			Recommendation r = list.get(i);
			if(r.getId().equals(recom.getId())) {
				list.remove(r);
				removed=true;
			}
		}
		i++;
	}
	
	/**
	 * Method to clean recommendation list
	 * @param list pre-final recommendation list
	 * @param validValues Valid values that final list can contain
	 */
	private void cleanRecomList(List<Recommendation> list, List<Recommendation> validValues) {
		
		Iterator i = list.iterator();
	    Recommendation recom;
	    
	    while (i.hasNext()) {
	    	recom = (Recommendation) i.next();
	    	
	    	if (!validValues.contains(recom)) {
	    		if(recom.getPromo() == null || !recom.getPromo()) {
	    			i.remove();
	    		}
	    		
	         }
	      }
	}
}
