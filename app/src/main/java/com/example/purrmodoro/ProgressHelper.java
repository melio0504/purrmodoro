package com.example.purrmodoro;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressHelper {
    private static final String PREFS_NAME = "PurrmodoroProgress";
    private static final String KEY_COMPLETED_SESSIONS = "completed_sessions";

    public static void incrementCompletedSessions(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int current = prefs.getInt(KEY_COMPLETED_SESSIONS, 0);
        prefs.edit().putInt(KEY_COMPLETED_SESSIONS, current + 1).apply();
    }

    public static int getCompletedSessions(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_COMPLETED_SESSIONS, 0);
    }

    public static void resetProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_COMPLETED_SESSIONS, 0).apply();
    }
}

