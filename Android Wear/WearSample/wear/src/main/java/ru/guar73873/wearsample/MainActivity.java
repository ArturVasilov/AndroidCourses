package ru.guar73873.wearsample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watchViewStub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub watchViewStub) {
                mTextView = (TextView) watchViewStub.findViewById(R.id.text);
            }
        });

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        WearableMessageReceiver messageReceiver = new WearableMessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
    }

    private class WearableMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(@NonNull Context context, @NonNull Intent intent) {
            String message = intent.getStringExtra("message");
            mTextView.setText(message);
        }
    }

}








    /*

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            WearableListView listView =
                    (WearableListView) findViewById(R.id.wearableList);

            List<String> items = new ArrayList<String>() {{
                    add("Item 1"); add("Item 2"); add("Item 3");
                    add("Item 4"); add("Item 5"); add("Item 6");
                    add("Item 7"); add("Item 8"); add("Item 9");
                    add("Item 10");
                }};

            listView.setAdapter(new WearableListAdapter(this, items));

            listView.setClickListener(this);
        }

        @Override
        public void onClick(WearableListView.ViewHolder v) {
            Integer tag = (Integer) v.itemView.getTag();
            // use this data to complete some action ...
        }

        @Override
        public void onTopEmptyRegionClick() {
        }

    */





    /*

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mDelayedConfirmationView = (DelayedConfirmationView)
                    findViewById(R.id.delayedConfirmation);
            mDelayedConfirmationView.setListener(this);

            startTimer();
        }

        private void startTimer() {
            mDelayedConfirmationView.setTotalTimeMs(5000);
            mDelayedConfirmationView.start();
        }

        @Override
        public void onTimerFinished(View view) {
            // User didn't cancel, perform the action
            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                    ConfirmationActivity.SUCCESS_ANIMATION);
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Success");
            startActivity(intent);
        }

        @Override
        public void onTimerSelected(View view) {
            // User canceled, abort the action
            Intent intent = new Intent(this, ConfirmationActivity.class);
            intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                    ConfirmationActivity.FAILURE_ANIMATION);
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Fail");
            startActivity(intent);
        }

     */




