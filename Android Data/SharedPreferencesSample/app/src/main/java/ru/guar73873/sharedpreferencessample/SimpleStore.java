package ru.guar73873.sharedpreferencessample;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SimpleStore {

    private static final String PREFERENCES_NAME = "my_prefs";

    private static final String COUNT_KEY = "count";

    private final SharedPreferences mSharedPreferences;

    public SimpleStore(Activity activity) {
        mSharedPreferences = activity.getSharedPreferences(
                PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void save(int count) {
        mSharedPreferences.edit().putInt(COUNT_KEY, count).apply();
    }

    public int getCount() {
        return mSharedPreferences.getInt(COUNT_KEY, 0);
    }
}


