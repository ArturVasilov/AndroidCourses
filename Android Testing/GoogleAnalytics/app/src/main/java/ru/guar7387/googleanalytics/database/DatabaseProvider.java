package ru.guar7387.googleanalytics.database;

import java.util.List;

import ru.guar7387.googleanalytics.data.TodoItem;

public interface DatabaseProvider {

    public void close();

    public void addTodoItem(TodoItem todoItem);

    public List<TodoItem> getAll();

    public void removeItem(TodoItem todoItem);

}
