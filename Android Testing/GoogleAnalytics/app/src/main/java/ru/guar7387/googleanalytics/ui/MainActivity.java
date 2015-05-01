package ru.guar7387.googleanalytics.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ru.guar7387.googleanalytics.R;
import ru.guar7387.googleanalytics.data.TodoItem;
import ru.guar7387.googleanalytics.database.DatabaseDefaultProvider;
import ru.guar7387.googleanalytics.database.DatabaseProvider;
import ru.guar7387.googleanalytics.utils.analytics.MyApplication;

public class MainActivity extends ActionBarActivity implements OnTodoCreated, OnCreateTodo, OnTodoItemRemoved {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;

    private DatabaseProvider mDatabaseProvider;

    private MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MyApplication) getApplication();
        application.sendScreenReport(TAG);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getFragmentManager();
        mDatabaseProvider = new DatabaseDefaultProvider(getApplicationContext());
        openList();
    }

    @Override
    public void onBackPressed() {
        application.sendEventReport(TAG, "onBackPressed", "");
        if (mFragmentManager.getBackStackEntryCount() >= 1) {
            mFragmentManager.popBackStackImmediate();
            mFragmentManager.beginTransaction().commit();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            throw new Exception("You will not destroy my app!");
        } catch (Exception e) {
            application.sendExceptionReport("App trying better to crash app than to stop it", false);
        }
        mDatabaseProvider.close();
    }

    @Override
    public void onTodoItemCreated(TodoItem todoItem) {
        onBackPressed();
        mDatabaseProvider.addTodoItem(todoItem);
        openList();
    }

    @Override
    public void onCreateTodo() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = new TodoCreationFragment();
        transaction.addToBackStack("");
        transaction.setCustomAnimations(R.anim.fragment_right_in, R.anim.fragment_right_out,
                R.anim.fragment_left_in, R.anim.fragment_left_out);
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onTodoItemRemove(TodoItem todoItem) {
        mDatabaseProvider.removeItem(todoItem);
    }

    private void openList() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = new TodoListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(TodoListFragment.KEY, (ArrayList<? extends Parcelable>) mDatabaseProvider.getAll());
        fragment.setArguments(arguments);
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

}
