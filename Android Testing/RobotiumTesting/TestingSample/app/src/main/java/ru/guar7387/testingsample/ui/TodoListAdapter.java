package ru.guar7387.testingsample.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.guar7387.testingsample.R;
import ru.guar7387.testingsample.data.TodoItem;

public class TodoListAdapter extends ArrayAdapter<TodoItem> {

    private final Activity activity;

    private final List<TodoItem> todoItems;

    public TodoListAdapter(Activity activity, int resource, List<TodoItem> todoItems) {
        super(activity, resource, todoItems);
        this.todoItems = todoItems;
        this.activity = activity;
    }

    private class ViewHolder {
        public TextView text;
        public TextView time;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View item = convertView;
        if (item == null) {
            item = activity.getLayoutInflater().inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) item.findViewById(R.id.todoText);
            holder.time = (TextView) item.findViewById(R.id.todoTime);
            item.setTag(holder);
        }
        else {
            holder = (ViewHolder) item.getTag();
        }

        TodoItem todoItem = todoItems.get(position);

        holder.text.setText(todoItem.getTask());
        holder.time.setText(new SimpleDateFormat("dd.MM.yyyy, EEE, HH:mm", Locale.getDefault()).format(new Date(todoItem.getTime())));

        return item;
    }
}
