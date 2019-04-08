package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Controller
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @PostMapping("/meals")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @PostMapping("/meals/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
        //model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/meals/delete&id={id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/update&id={id}")
    public String update(@PathVariable(value = "id") Integer id, Model model) {
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "update");
        return "mealForm";
    }

    @GetMapping("/meals/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now(), "", 0);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
