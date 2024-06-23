package com.example.parcial_uno_app.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {
    private static final String DARK_MODE = "DARK_MODE";

    public static void saveDarkModeState(Context context, boolean isDarkModeOn) {
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(DARK_MODE, isDarkModeOn);
        editor.apply();
    }

    public static boolean loadDarkModeState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", MODE_PRIVATE);
        return prefs.getBoolean(DARK_MODE, false);
    }
}
