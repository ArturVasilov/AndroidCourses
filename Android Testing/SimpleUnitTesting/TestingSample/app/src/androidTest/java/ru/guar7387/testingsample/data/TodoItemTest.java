package ru.guar7387.testingsample.data;

import junit.framework.TestCase;

import org.junit.Before;

public class TodoItemTest extends TestCase {

    private TodoItem todoItem;

    @Before
    public void setUp() throws Exception {
        todoItem = new TodoItem("Item", 1000 * 1000 * 1000 * 1000L);
    }

    public void testGetTask() throws Exception {
        assertEquals(todoItem.getTask(), "Item");
    }

    public void testGetTime() throws Exception {
        assertEquals(todoItem.getTime(), 1000 * 1000 * 1000 * 1000L);
    }

    public void testToString() throws Exception {
        assertEquals(todoItem.toString(),
                "TodoItem{" +
                        "task='" + "Item" + '\'' +
                        ", time=" + String.valueOf(1000 * 1000 * 1000 * 1000L) +
                        '}');
    }

    public void testSetId() throws Exception {
        todoItem.setId(5);
        assertEquals(todoItem.getId(), 5);
        todoItem.setId(10);
        assertEquals(todoItem.getId(), 10);
    }
}


