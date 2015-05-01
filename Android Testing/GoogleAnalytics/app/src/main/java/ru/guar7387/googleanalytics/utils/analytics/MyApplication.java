package ru.guar7387.googleanalytics.utils.analytics;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

import ru.guar7387.googleanalytics.utils.Logger;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static final String PROPERTY_ID = "UA-57236542-2";

    private final Map<TrackerName, Tracker> mTrackers = new HashMap<>();

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            Logger.i(TAG, "creating tracker");
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID) : null;
            if (t != null) {
                t.enableExceptionReporting(true);
                t.enableAdvertisingIdCollection(true);
            }
            //false - to send reports
            //true to enable debugging without it
            GoogleAnalytics.getInstance(this).setAppOptOut(false);
            mTrackers.put(trackerId, t);
        }
        Logger.i(TAG, "returning tracker");
        return mTrackers.get(trackerId);
    }

    public void sendScreenReport(String screenName) {
        Logger.i(TAG, "Trying to send report about screen to google analytics");
        Tracker t = getTracker(TrackerName.APP_TRACKER);
        t.setScreenName(screenName);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    public void sendEventReport(String category, String action, String label) {
        Tracker t = getTracker(TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label).build());
    }

    public void sendExceptionReport(String description, boolean isFatal) {
        Tracker t = getTracker(TrackerName.APP_TRACKER);
        t.send(new HitBuilders.ExceptionBuilder()
                .setDescription(description)
                .setFatal(isFatal)
                .build());
    }

}


