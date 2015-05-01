package ru.guar7387.articlesmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import ru.guar7387.articlesmanager.slidingtabs.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {

    /**
     * This app may be useful for you in both ways : in source code and using it directly.
     * It containt a set of links devoted to Android 5.0 and Material design.
     * I have used most popular new widgets, such as toolbar, SlidingTabLayout + ViewPager, RecyclerView and CardView.
     * In my opinion this is a basic template which can be used for many apps, and this app shows Android 5.0 in action, as I hope.
     * I have added a javadoc to almost all classes and methods, except obvious and standard things.
     * I hope, you'll enjoy this app and find it useful for youself.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init our articles
        //again I can say, that this part isn't important, I haven't worried about perfomance
        ArticlesStorage.INSTANCE.fillArticles();

        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //this method is needed to make our toolbar work as ActionBar
        setSupportActionBar(toolbar);

        //init view pager and set it's adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter());

        //init sliding tab layout and set colors to it
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white_color);
            }

            @Override
            public int getDividerColor(int position) {
                return 0x00444444;
            }
        });
        //assign tab layout with view pager to make them work together
        mSlidingTabLayout.setViewPager(viewPager);
    }

    //method to open a given url (in browser for example)
    private void openUrl(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * custom adapter for viewpager
     */
    private class PagerAdapter extends android.support.v4.view.PagerAdapter {

        /**
         * @return count of tabs: All, Material Design, Widgets - 3.
         */
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //noinspection ObjectEquality
            return object == view;
        }

        /**
         * @param position - position of chosen tab
         * @return title for tab with this position
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? getString(R.string.all) :
                    position == 1 ? getString(R.string.material_design) :
                            getString(R.string.widgets);
        }

        /**
         * creates for tab with this position
         * @param position - position of chosen tab
         * @return view for tab with this position
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //inflating view - it's RecyclerView
            RecyclerView recyclerView = (RecyclerView) getLayoutInflater().inflate(R.layout.articles_pager_item, container, false);

            //very important part of setting up RecyclerView
            //we need to assign horizontal or vertical layout manager to it, or app will crashes.
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            //adapter for list
            ArticlesAdapter adapter;

            //anonymous class for callback mechanism
            //calls when list item is clicked
            ArticlesAdapter.ItemClickListener itemClickListener = new ArticlesAdapter.ItemClickListener() {
                @Override
                public void onItemClicked(Article article) {
                    openUrl(article.getUrl());
                }
            };
            //...

            //getting we needed list of articles and creating adatper
            switch (position) {
                case 0:
                    adapter = new ArticlesAdapter(
                            ArticlesStorage.INSTANCE.getArticlesByTag(Tags.ALL),
                            itemClickListener);
                    break;

                case 1:
                    adapter = new ArticlesAdapter(
                            ArticlesStorage.INSTANCE.
                                    getArticlesByTag(Tags.MATERIAL),
                            itemClickListener);
                    break;

                default:
                    adapter = new ArticlesAdapter(
                            ArticlesStorage.INSTANCE.getArticlesByTag(Tags.WIDGETS),
                            itemClickListener);
                    break;
            }

            //set adapter for recycler
            recyclerView.setAdapter(adapter);

            //adding view to container to display it.
            container.addView(recyclerView);

            return recyclerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //remove view from container to give place for another view
            container.removeView((View) object);
        }
    }
}
