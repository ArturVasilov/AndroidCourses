package ru.guar7387.surfaceviewsample;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameView gameView = new GameView(getApplicationContext());
        setContentView(gameView);
    }
}



