package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealDAOimpl implements MealDAO {
    private static Map<Integer, Meal> mapMeal = new ConcurrentHashMap<>();

    static {
        for (Meal meal : MealsUtil.mealList) {
            mapMeal.put(meal.getId(), meal);
        }
    }

    @Override
    public void add(Meal meal) {
        mapMeal.put(meal.getId(), meal);
    }

    @Override
    public void remove(int id) {
        mapMeal.remove(id);
    }

    @Override
    public void update(int id, LocalDateTime localDateTime, String description, int calories) {
        mapMeal.get(id).setDateTime(localDateTime);
        mapMeal.get(id).setDescription(description);
        mapMeal.get(id).setCalories(calories);
    }

    @Override
    public Meal get(int id) {
        return mapMeal.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapMeal.values());
    }
}
