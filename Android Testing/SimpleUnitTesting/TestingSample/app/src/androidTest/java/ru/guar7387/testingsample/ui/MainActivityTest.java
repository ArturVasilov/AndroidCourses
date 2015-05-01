package ru.guar7387.testingsample.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import junit.framework.Assert;

import java.util.List;

import ru.guar7387.testingsample.data.TodoItem;
import ru.guar7387.testingsample.database.DatabaseProvider;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testOnCreate() throws Exception {
        assertNotNull(mActivity);
        assertNotNull(mActivity.mDatabaseProvider);
    }

    public void testOnCreateTodo() throws Exception {
        mActivity.onCreateTodo();
        assertNotNull(mActivity);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mActivity = null;
    }
}


