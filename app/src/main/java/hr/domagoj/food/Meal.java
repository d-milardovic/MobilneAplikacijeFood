package hr.domagoj.food;

import androidx.annotation.NonNull;

public class Meal {
    String name;
    int cal;


    Meal(String name, int cal) {
        this.name = name;
        this.cal = cal;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + cal + "cal)";
    }
}