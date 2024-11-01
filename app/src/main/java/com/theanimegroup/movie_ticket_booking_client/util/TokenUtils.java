package com.theanimegroup.movie_ticket_booking_client.util;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class TokenUtils {
    public static String getAuthToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MovieAppPrefsToken", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);
        return token != null ? token : "";
    }

    public static void saveAuthToken(Context context, String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MovieAppPrefsToken", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }

    public static void removeAuthToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MovieAppPrefsToken", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("auth_token");
        editor.apply();
    }

}
