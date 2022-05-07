package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<LocalDate, Integer> map = new HashMap<>();
        for (UserMeal elem : meals) {
            LocalDate localDate = elem.getDateTime().toLocalDate();
            if (map.containsKey(localDate)) {
                map.put(localDate, map.get(localDate) + elem.getCalories());
            } else {
                map.put(localDate, elem.getCalories());
            }
        }
        for (UserMeal elem : meals) {
            int mealTimeInMinute = elem.getDateTime().getHour() * 60 + elem.getDateTime().getMinute();
            int startTimeInMinute = startTime.getHour() * 60 + startTime.getMinute();
            int endTimeInMinute = endTime.getHour() * 60 + endTime.getMinute();
            LocalDate mealDate = elem.getDateTime().toLocalDate();
            if (startTimeInMinute < mealTimeInMinute & endTimeInMinute > mealTimeInMinute) {
                list.add(new UserMealWithExcess(elem.getDateTime(), elem.getDescription(), elem.getCalories(), map.get(mealDate) > caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
