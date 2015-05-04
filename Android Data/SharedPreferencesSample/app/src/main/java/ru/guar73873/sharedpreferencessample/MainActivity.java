package ru.guar73873.sharedpreferencessample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private int mCount;

    private SimpleStore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStore = new SimpleStore(this);

        defaultSharedPreferences();
        namedSharedPreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCount = mStore.getCount();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStore.save(mCount);
    }

    private void defaultSharedPreferences() {
        final String VALUE_KEY = "SOME_KEY";
        //default preferences file for this Activity
        //MODE_PRIVATE means that this file can only be accessed by the calling application
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //get the editor object, put int value and save
        preferences.edit().putInt(VALUE_KEY, 5).apply();

        //getting saved value
        //second param is a default value (if there are no such key)
        int value = preferences.getInt("SOME_KEY", 0);
        if (value != 5) {
            throw new IllegalStateException("Failed to save value for preferences");
        }
    }

    private void namedSharedPreferences() {
        final String FILENAME = "my_prefs";
        final String VALUE_KEY = "SOME_KEY";
        //default preferences file for this Activity
        //MODE_PRIVATE means that this file can only be accessed by the calling application
        SharedPreferences preferences = getSharedPreferences(FILENAME, MODE_PRIVATE);

        //get the editor object
        SharedPreferences.Editor editor = preferences.edit();
        //put some values
        //editor uses builder pattern
        editor.putInt(VALUE_KEY, 5)
                .putString("STRING_KEY", "Hello, World!")
                .putStringSet("STRING_SET_KEY", new HashSet<String>() {{
                    add("Hello");
                    add(", ");
                    add("World!");
                }});
        //save all changes
        //editor.commit() is also possible, but apply method is asynchronous and recommended
        editor.apply();

        //getting saved value
        //second param is a default value (if there are no such key)
        int value = preferences.getInt("SOME_KEY", 0);
        if (value != 5) {
            throw new IllegalStateException("Failed to save value for preferences");
        }
    }
}
