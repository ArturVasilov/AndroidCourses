package ru.guar7387.testingsample.ui;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.guar7387.testingsample.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testOnCreate() throws Exception {
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        assertTrue("failed to find text", solo.searchText("екккаа"));
    }

    public void testAddTask() throws Exception {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return solo.searchText("date");
            }
        }, 500);

        solo.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText name = (EditText) solo.getView(R.id.textTodo);
                EditText date = (EditText) solo.getView(R.id.date);
                EditText time = (EditText) solo.getView(R.id.time);
                name.setText("Test");
                date.setText("25.02.2015");
                time.setText("18:00");
            }
        });
        solo.clickOnView(solo.getView(R.id.fab));
        assertTrue("Failed to add task", solo.searchText("Test"));
    }

    public void testCreationFragment() throws Exception {
        solo.clickOnView(solo.getView(R.id.fab));
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return solo.searchText("date");
            }
        }, 500);

        solo.clickOnView(solo.getView(R.id.date));
        DatePicker datePicker = (DatePicker) solo.getView("datePicker");
        assertNotNull(datePicker);
        View okButton = solo.getText("ОК");
        assertNotNull(okButton);
        solo.clickOnView(okButton);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return solo.searchText("2015");
            }
        }, 500);
        EditText date = (EditText) solo.getView(R.id.date);
        assertEquals(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).
                format(Calendar.getInstance().getTime()), date.getText().toString());

        solo.clickOnView(solo.getView(R.id.time));
        TimePicker timePicker = (TimePicker) solo.getView("timePicker");
        assertNotNull(timePicker);
        okButton = solo.getText("ОК");
        assertNotNull(okButton);
        solo.clickOnView(okButton);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return solo.searchText(":");
            }
        }, 500);
        EditText time = (EditText) solo.getView(R.id.time);
        assertEquals(new SimpleDateFormat("hh:mm a", Locale.getDefault()).
                format(Calendar.getInstance().getTime()), time.getText().toString());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}


