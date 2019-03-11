package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.util.DateTimeUtil.adjustEndDateTime;
import static ru.javawebinar.topjava.util.DateTimeUtil.adjustStartDateTime;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_BREAKFAST.getId(), USER_ID);
        assertMatch(meal, USER_BREAKFAST);
    }

    @Test
    public void delete() {
        service.delete(USER_DINNER.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.JUNE, 1), USER_ID);
        assertMatch(meals, USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.JUNE, 1, 13, 0, 0),
                LocalDateTime.of(2015, Month.JUNE, 1, 16, 0, 0), ADMIN_ID);
        assertMatch(meals, ADMIN_LUNCH);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertMatch(meals, ADMIN_DINNER, ADMIN_LUNCH);
    }

    @Test
    public void update() {
        Meal meal = new Meal(ADMIN_DINNER);
        meal.setDateTime(LocalDateTime.of(2300, Month.APRIL, 14, 13,13,13));
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(ADMIN_DINNER.getId(), ADMIN_ID), meal);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.of(2015, Month.JUNE,1, 9,0,0), "Admin breakfast", 10000);
        service.create(meal, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_DINNER, ADMIN_LUNCH, meal);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(USER_DINNER.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(ADMIN_DINNER.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(ADMIN_DINNER, USER_ID);
    }
}