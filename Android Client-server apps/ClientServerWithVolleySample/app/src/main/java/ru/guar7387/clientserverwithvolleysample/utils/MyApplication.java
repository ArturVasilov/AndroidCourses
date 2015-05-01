package ru.guar7387.clientserverwithvolleysample.utils;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import ru.guar7387.clientserverwithvolleysample.data.User;

public class MyApplication extends Application {

    private RequestQueue mRequestQueue;

    private User mCurrentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mCurrentUser = new User(12, "testaccount@gmail.com", "123456", "TestUser");
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public User getCurrentUser() {
        return mCurrentUser;
    }
}


