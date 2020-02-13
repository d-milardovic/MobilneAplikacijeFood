package hr.domagoj.food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;

public class DailyLog {

    Date date;
    int cal;
    ArrayList<Meal>meals;

    DailyLog(Date date, int cal) {
        this.date = date;
        this.cal = cal;
        meals = new ArrayList<>();
    }

    void addMeal(Meal meal) {
        meals.add(meal);
    }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat date =  new SimpleDateFormat("dd.MM.yyyy.");

        return date.format(this.date) + " - " + cal + " cal";
    }
}
