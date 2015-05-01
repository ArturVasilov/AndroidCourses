package ru.guar7387.testingsample.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.guar7387.testingsample.R;
import ru.guar7387.testingsample.data.TodoItem;
import ru.guar7387.testingsample.fab.FabGenerator;
import ru.guar7387.testingsample.fab.FloatingActionButton;

public class TodoCreationFragment extends Fragment implements View.OnClickListener {

    private EditText mTodoText;

    private EditText mDateEdit;

    private EditText mTimeEdit;

    private Calendar mCalendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_todo, container, false);

        init(root);

        mCalendar = Calendar.getInstance();

        return root;
    }

    private void init(View root) {
        mTodoText = (EditText) root.findViewById(R.id.textTodo);
        mDateEdit = (EditText) root.findViewById(R.id.date);
        mTimeEdit = (EditText) root.findViewById(R.id.time);

        mDateEdit.setOnClickListener(this);
        mTimeEdit.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        FabGenerator.createFab(getActivity().getApplicationContext(), fab, R.drawable.fab_ok, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                DateFragmentDialog dateFragmentDialog = new DateFragmentDialog();
                dateFragmentDialog.setDatePickedListener(new DateFragmentDialog.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(Calendar date) {
                        mCalendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
                        mCalendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
                        mCalendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
                        mDateEdit.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(mCalendar.getTime()));
                    }
                });
                dateFragmentDialog.show(getFragmentManager(), "");
                break;

            case R.id.time:
                TimeFragmentDialog timeFragmentDialog = new TimeFragmentDialog();
                timeFragmentDialog.setTimePickedListener(new TimeFragmentDialog.OnTimePickedListener() {
                    @Override
                    public void onTimePicked(Calendar time) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
                        mCalendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
                        mTimeEdit.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(mCalendar.getTime()));
                    }
                });
                timeFragmentDialog.show(getFragmentManager(), "");
                break;

            case R.id.fab:
                if (mTodoText.getText() == null || mTodoText.getText().toString().isEmpty()) {
                    mTodoText.setBackgroundColor(Color.RED);
                    return;
                }
                else {
                    mTodoText.setBackgroundColor(Color.TRANSPARENT);
                }
                if (mDateEdit.getText() == null || mDateEdit.getText().toString().isEmpty()) {
                    mDateEdit.setBackgroundColor(Color.RED);
                    return;
                }
                else {
                    mDateEdit.setBackgroundColor(Color.TRANSPARENT);
                }
                if (mTimeEdit.getText() == null || mTimeEdit.getText().toString().isEmpty()) {
                    mTimeEdit.setBackgroundColor(Color.RED);
                    return;
                }
                else {
                    mTimeEdit.setBackgroundColor(Color.TRANSPARENT);
                }
                ((OnTodoCreated) getActivity()).onTodoItemCreated(new TodoItem(mTodoText.getText().toString(), mCalendar.getTimeInMillis()));
                break;
        }
    }

    public static class DateFragmentDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public static interface OnDatePickedListener {
            public void onDatePicked(Calendar date);
        }

        private OnDatePickedListener mListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setDatePickedListener(OnDatePickedListener listener) {
            this.mListener = listener;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            mListener.onDatePicked(c);
        }
    }

    public static class TimeFragmentDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        public static interface OnTimePickedListener {
            public void onTimePicked(Calendar time);
        }

        private OnTimePickedListener mListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, !DateFormat.is24HourFormat(getActivity().getApplicationContext()));
        }

        public void setTimePickedListener(OnTimePickedListener listener) {
            this.mListener = listener;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);

            mListener.onTimePicked(c);
        }
    }
}
