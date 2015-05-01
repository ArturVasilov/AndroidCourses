package ru.guar7387.testingsample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import ru.guar7387.testingsample.data.TodoItem;

public class DatabaseDefaultProvider implements DatabaseConstants, DatabaseProvider {

    private final SQLiteDatabase sqLiteDatabase;

    private final List<TodoItem> todoItems = new ArrayList<>();

    public DatabaseDefaultProvider(Context context) {
        sqLiteDatabase = new TodoDatabaseHelper(context).getWritableDatabase();
    }

    @Override
    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
        ContentValues values = new ContentValues();
        values.put(TODO_TEXT, todoItem.getTask());
        values.put(TODO_TIME, todoItem.getTime());
        sqLiteDatabase.insertWithOnConflict(TODO_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public List<TodoItem> getAll() {
        if (!todoItems.isEmpty()) {
            return todoItems;
        }
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TODO_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            @SuppressWarnings("ObjectAllocationInLoop")
            TodoItem todoItem = new TodoItem(cursor.getString(1), cursor.getLong(2));
            todoItem.setId(cursor.getInt(0));
            todoItems.add(todoItem);
        }
        cursor.close();
        return todoItems;
    }

    @Override
    public void removeItem(TodoItem todoItem) {
        todoItems.remove(todoItem);
        sqLiteDatabase.delete(TODO_TABLE_NAME, BaseColumns._ID + " = " + todoItem.getId(), null);
    }

    @Override
    public void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

}
