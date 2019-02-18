package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {
    void add(Meal meal);

    void remove(int id);

    void update(int id, LocalDateTime localDateTime, String description, int calories);

    Meal get(int id);

    List<Meal> getAll();
}
