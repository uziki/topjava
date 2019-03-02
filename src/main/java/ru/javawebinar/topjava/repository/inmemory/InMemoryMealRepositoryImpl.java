package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (SecurityUtil.authUserId() != meal.getUserID()) {
            return null;
        } else if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        if (SecurityUtil.authUserId() == repository.get(id).getUserID()) {
            repository.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id) {
        if (SecurityUtil.authUserId() != repository.get(id).getUserID()) {
            return null;
        } else
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> list = new ArrayList<>();
        for (Meal meal : repository.values()) {
            if (meal.getUserID() == SecurityUtil.authUserId()) {
                list.add(meal);
            }
        }
        Collections.sort(list, (a, b) -> b.getDate().compareTo(a.getDate()));
        return list;
    }
}

