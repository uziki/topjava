package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOimpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static MealDAO dao;
    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDAOimpl();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String forwardPath = "";
        List<MealTo> mealsTo;
        if (action!=null) {
            if (action.equalsIgnoreCase("edit")) {
                log.debug("MealServlet doGet edit");
                forwardPath = "mealAddOrEdit.jsp";
                int mealID = Integer.parseInt(request.getParameter("mealID"));
                Meal meal = dao.get(mealID);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("delete")) {
                log.debug("MealServlet doGet delete");
                forwardPath = "meals.jsp";
                int mealID = Integer.parseInt(request.getParameter("mealID"));
                dao.remove(mealID);
                mealsTo = getMealsWithExcess();
                request.setAttribute("mealsTo", mealsTo);
            } else if (action.equalsIgnoreCase("add")) {
                log.debug("MealServlet doGet add");
                forwardPath = "mealAddOrEdit.jsp";
            }
        } else {
            log.debug("MealServlet doGet");
            forwardPath = "meals.jsp";
            mealsTo = getMealsWithExcess();
            request.setAttribute("mealsTo", mealsTo);
        }
        request.getRequestDispatcher(forwardPath).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("MealServlet doPost");
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        String dateTime = request.getParameter("dateTime");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (request.getParameter("id")!=null) {
            int id = Integer.parseInt(request.getParameter("id").trim());
            dao.update(id, localDateTime, description, calories);
        } else {
            Meal addedMeal = new Meal(localDateTime, description, calories);
            dao.add(addedMeal);
        }

        List<MealTo> mealsTo = getMealsWithExcess();
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    public static List<MealTo> getMealsWithExcess() {
        List<Meal> meals = dao.getAll();
        List<MealTo> mealsTo = MealsUtil.getFilteredWithExcess(meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay);
        return mealsTo;
    }


}
