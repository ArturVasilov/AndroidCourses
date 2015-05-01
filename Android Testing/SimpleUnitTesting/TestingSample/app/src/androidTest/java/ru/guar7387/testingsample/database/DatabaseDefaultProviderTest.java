package ru.guar7387.testingsample.database;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import java.util.List;

import ru.guar7387.testingsample.data.TodoItem;
import ru.guar7387.testingsample.ui.MainActivity;

public class DatabaseDefaultProviderTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private DatabaseProvider mDatabaseProvider;

    public DatabaseDefaultProviderTest() {
        super(MainActivity.class);
    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        mDatabaseProvider = new DatabaseDefaultProvider(getActivity().getApplicationContext());
    }

    public void testAddTodoItem() throws Exception {
        List<TodoItem> all = mDatabaseProvider.getAll();
        int size = all.size();
        TodoItem item = new TodoItem("Aaaaaaa", System.currentTimeMillis());
        mDatabaseProvider.addTodoItem(item);
        assertEquals(size + 1, all.size());
        assertEquals(true, all.contains(item));

        mDatabaseProvider.close();
        mDatabaseProvider = new DatabaseDefaultProvider(getActivity().getApplicationContext());
        assertEquals(size + 1, mDatabaseProvider.getAll().size());
        mDatabaseProvider.removeItem(item);
    }

    public void testGetAll() throws Exception {
        List<TodoItem> all = mDatabaseProvider.getAll();
        assertEquals(false, all.isEmpty());
    }

    public void testRemoveItem() throws Exception {
        List<TodoItem> all = mDatabaseProvider.getAll();
        TodoItem item = new TodoItem("Aaaa", System.currentTimeMillis());
        mDatabaseProvider.addTodoItem(item);
        int size = all.size();
        mDatabaseProvider.removeItem(item);
        assertEquals(size - 1, all.size());
        assertEquals(false, all.contains(item));

        mDatabaseProvider.close();
        mDatabaseProvider = new DatabaseDefaultProvider(getActivity().getApplicationContext());
        assertEquals(size - 1, mDatabaseProvider.getAll().size());
    }

    public void testClose() throws Exception {
        mDatabaseProvider.close();
        try {
            mDatabaseProvider.addTodoItem(new TodoItem("aa", System.currentTimeMillis()));
            fail("Database was not closed");
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}


