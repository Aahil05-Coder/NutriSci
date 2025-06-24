// src/service/VisualizationService.java
package service;

import dao.MealRecordDAO;
import model.MealRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds data summaries for charting and visualization.
 */
public class VisualizationService {
    private final MealRecordDAO mealDao = new MealRecordDAO();

    /**
     * Computes the average calories per meal type.
     * @return map from meal type (e.g. "BREAKFAST") to average calories
     */
    
    public Map<String, Double> computeAverageDistribution(int userId) throws Exception {
        List<MealRecord> all = mealDao.findAllByUser(userId);
        Map<String, Double> sumMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (MealRecord r : all) {
            String type = r.getMealType();
            sumMap.put(type, sumMap.getOrDefault(type, 0.0) + r.getCalories());
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }
        Map<String, Double> avgMap = new HashMap<>();
        for (String type : sumMap.keySet()) {
            double sum = sumMap.get(type);
            int cnt = countMap.get(type);
            avgMap.put(type, cnt > 0 ? sum / cnt : 0.0);
        }
        return avgMap;
    }

}
