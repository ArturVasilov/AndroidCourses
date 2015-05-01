package ru.guar7387.testingsample.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.junit.Before;

import java.util.ArrayList;

import ru.guar7387.testingsample.R;

public class TodoListFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private TodoListFragment fragment;

    public TodoListFragmentTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        MainActivity activity = getActivity();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        fragment = new TodoListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(TodoListFragment.KEY,
                (ArrayList<? extends Parcelable>) activity.mDatabaseProvider.getAll());
        fragment.setArguments(arguments);
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void testOnCreateView() throws Exception {
        assertNotNull(fragment);
        Bundle args = fragment.getArguments();
        assertEquals(getActivity().mDatabaseProvider.getAll().size(),
                args.getParcelableArrayList(TodoListFragment.KEY).size());

        View view = fragment.onCreateView(getActivity().getLayoutInflater(), null, null);
        assertNotNull(view);
    }
}

