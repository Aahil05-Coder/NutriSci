package service;

import dao.MealRecordDAO;
import model.MealRecord;

import java.time.LocalDate;
import java.util.List;

public class MealService {
    private final MealRecordDAO dao = new MealRecordDAO();

    public void addMeal(List<MealRecord> mealEntries) {
        for (MealRecord rec : mealEntries) {
            dao.insert(rec);
        }
    }

    public List<List<MealRecord>> getMealsByUser(int userId) {
        return dao.getMealsByUser(userId);
    }

    public void deleteMeal(int userId, LocalDate date, String mealType) {
        dao.deleteMeal(userId, date, mealType);
    }
}
