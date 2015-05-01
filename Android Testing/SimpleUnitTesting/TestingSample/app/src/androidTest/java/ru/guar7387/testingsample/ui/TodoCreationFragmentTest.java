package ru.guar7387.testingsample.ui;

import android.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import org.junit.Before;

import ru.guar7387.testingsample.R;

public class TodoCreationFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private TodoCreationFragment fragment;

    private View root;

    public TodoCreationFragmentTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        fragment = new TodoCreationFragment();
        transaction.addToBackStack("");
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
        root = fragment.onCreateView(getActivity().getLayoutInflater(), null, null);
    }

    public void testOnCreateView() throws Exception {
        assertEquals(true, fragment != null);
        assertEquals(true, root != null);
    }

    public void testOnClick() throws Exception {
        EditText editText = (EditText) root.findViewById(R.id.textTodo);
        editText.setText("AAAA");
        root.findViewById(R.id.fab).performClick();
    }
}


