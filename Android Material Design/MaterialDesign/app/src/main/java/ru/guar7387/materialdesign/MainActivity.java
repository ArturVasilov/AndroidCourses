package ru.guar7387.materialdesign;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawer;

    private final String[] mDrawerItems = {"Item 1", "Item 2", "Item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        mDrawer = (ListView) findViewById(R.id.left_drawer);
        mDrawer.setAdapter(new ArrayAdapter<>(
                this, R.layout.drawer_item, mDrawerItems));
        mDrawer.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectItem(position);
                    }
                });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerTitle = getTitle();
        mTitle = getTitle();
        createToggle();
    }

    private void selectItem(int position) {
        mTitle = mDrawerItems[position];
        mDrawerLayout.closeDrawer(mDrawer);

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FirstFragment();
                break;

            case 1:
                fragment = new SecondFragment();
                break;

            case 2:
                fragment = new ThirdFragment();
                break;
        }
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

    private void createToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState
        // has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}


