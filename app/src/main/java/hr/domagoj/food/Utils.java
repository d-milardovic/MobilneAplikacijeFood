package hr.domagoj.food;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

class Utils {

    private static final String MEALS_KEY = "meals";
    private static final String LOGS_KEY = "logs";
    private static final String LIMIT_KEY = "limit";

    private static final Meal[] predefinedMeals = new Meal[] {
            new Meal("Burger", 200),
            new Meal("Pizza", 300),

    };

    static void addMeal(Meal meal, Context context) {
        ArrayList<Meal> meals = getMealsFromStorage(context);

        meals.add(meal);

        Gson gson = new Gson();
        String jsonString = gson.toJson(meals);

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(MEALS_KEY, jsonString)
                .apply();
    }

    static ArrayList<Meal> getMeals(Context context) {
        ArrayList<Meal> meals = new ArrayList<>(Arrays.asList(predefinedMeals));
        meals.addAll(getMealsFromStorage(context));

        return meals;
    }

    private static ArrayList<Meal> getMealsFromStorage(Context context) {
        String mealString = PreferenceManager.getDefaultSharedPreferences(context).getString(MEALS_KEY, "[]");

        Type mealType = new TypeToken<ArrayList<Meal>>() {}.getType();
        Gson gson = new Gson();

        return gson.fromJson(mealString, mealType);
    }

    static void addToLog(Meal meal, Context context) {
        ArrayList<DailyLog> dailyLogs = getLogs(context);

        int todayLogIndex = getTodayLogIndex(context);

        if(todayLogIndex != -1) {
            DailyLog todayLog = dailyLogs.get(todayLogIndex);
            todayLog.cal += meal.cal;
            dailyLogs.set(todayLogIndex, todayLog);
        } else {
            dailyLogs.add(new DailyLog(new Date(), meal.cal));
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(dailyLogs);

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(LOGS_KEY, jsonString)
                .apply();
    }

    static ArrayList<DailyLog> getLogs(Context context) {
        String logsString = PreferenceManager.getDefaultSharedPreferences(context).getString(LOGS_KEY, "[]");

        Type logType = new TypeToken<ArrayList<DailyLog>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(logsString, logType);
    }

    private static int getTodayLogIndex(Context context) {
        ArrayList<DailyLog> dailyLogs = getLogs(context);

        for(int i = 0; i < dailyLogs.size(); i++) {
            if(DateUtils.isToday(dailyLogs.get(i).date.getTime())) {
                return i;
            }
        }
        return -1;
    }

    static int getTodayCal(Context context) {
        ArrayList<DailyLog> dailyLogs = getLogs(context);

        for(int i = 0; i < dailyLogs.size(); i++) {
            if(DateUtils.isToday(dailyLogs.get(i).date.getTime())) {
                return dailyLogs.get(i).cal;
            }
        }
        return 0;
    }


    static int getDailyLimit(Context context) {
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getInt(LIMIT_KEY, 0);
    }

    static void setDailyLimit(Context context, int limit) {
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putInt(LIMIT_KEY, limit)
                .apply();
    }
}


