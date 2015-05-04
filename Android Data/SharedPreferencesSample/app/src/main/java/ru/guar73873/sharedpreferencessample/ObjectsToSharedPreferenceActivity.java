package ru.guar73873.sharedpreferencessample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

public class ObjectsToSharedPreferenceActivity extends Activity {

    private User mUser;

    private static final String USER_KEY = "user";

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        mUser = new User("Artur", 20);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String json = preferences.getString(USER_KEY, "");
        mUser = mGson.fromJson(json, User.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPreferences(MODE_PRIVATE).edit().
                putString(USER_KEY, mGson.toJson(mUser))
                .apply();
    }
}
