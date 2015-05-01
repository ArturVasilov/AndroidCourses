package ru.guar73873.wearsample;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationsExamples {

    private final Context mContext;

    public NotificationsExamples(Context context) {
        this.mContext = context;
    }

    private void showSimpleNotification() {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(mContext,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Hello, World!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(notificationPendingIntent);
        showNotification(builder);
    }

    private void showNotificationWithAction() {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(mContext,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent googleIntent = new Intent(Intent.ACTION_VIEW);
        googleIntent.setData(Uri.parse("https://google.com"));
        PendingIntent googlePendingIntent = PendingIntent.getActivity(
                mContext, 1, googleIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mContext.getString(R.string.app_name))
                        .setContentText(mContext.getString(R.string.hello_world))
                        .setContentIntent(notificationPendingIntent)
                        .addAction(android.R.drawable.btn_plus,
                                "Open google", googlePendingIntent);
        showNotification(builder);
    }

    private void showNotificationWithBigText() {
        String bigText = "Here must be some large text, such as email or smth like this." +
                "Oh fuck it, I'm really tired to write here some text.. Oh, lorem ipsum help!" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nunc vulputate mi libero, in semper turpis fermentum vel. " +
                "Cras ante ex, pharetra id mattis eget, hendrerit at elit. ";
        NotificationCompat.BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
        bigStyle.bigText(bigText);

        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(mContext,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent googleIntent = new Intent(Intent.ACTION_VIEW);
        googleIntent.setData(Uri.parse("https://google.com"));
        PendingIntent googlePendingIntent = PendingIntent.getActivity(
                mContext, 1,
                googleIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                mContext.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle("Big text")
                        .setContentText("Here is some content text")
                        .setContentIntent(notificationPendingIntent)
                        .addAction(android.R.drawable.btn_plus,
                                "Open google", googlePendingIntent)
                        .setStyle(bigStyle);
        showNotification(builder);
    }

    private void sendNotificationGroup() {
        final String NOTIFICATIONS_GROUP_KEY = "group";
        NotificationCompat.Builder mainNotificationBuilder =
                new NotificationCompat.Builder(mContext);
        mainNotificationBuilder.setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Hello, World!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGroup(NOTIFICATIONS_GROUP_KEY);

        NotificationCompat.Builder secondNotificationBuilder =
                new NotificationCompat.Builder(mContext);
        secondNotificationBuilder.setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Second notification")
                .setSmallIcon(android.R.drawable.star_on)
                .setGroup(NOTIFICATIONS_GROUP_KEY);

        NotificationCompat.Builder thirdNotificationBuilder =
                new NotificationCompat.Builder(mContext);
        thirdNotificationBuilder.setContentTitle(mContext.getString(R.string.app_name))
                .setContentTitle("Some text from third notification")
                .setContentText("Third notification")
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setGroup(NOTIFICATIONS_GROUP_KEY);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(mContext);
        notificationManager.notify(1, mainNotificationBuilder.build());
        notificationManager.notify(2, secondNotificationBuilder.build());
        notificationManager.notify(3, thirdNotificationBuilder.build());
    }

    private void showNotificationWithPages() {
        NotificationCompat.Builder mainNotificationBuilder =
                new NotificationCompat.Builder(mContext);
        mainNotificationBuilder.setContentTitle(mContext.getString(R.string.app_name))
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Main notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                        R.mipmap.ic_launcher));

        Notification secondPageNotification = new NotificationCompat.Builder(mContext)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Second page")
                .build();

        Notification thirdPageNotification = new NotificationCompat.Builder(mContext)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText("Third page")
                .build();

        mainNotificationBuilder.extend(new NotificationCompat.WearableExtender()
                .addPage(secondPageNotification)
                .addPage(thirdPageNotification));

        showNotification(mainNotificationBuilder);
    }

    private void showNotification(NotificationCompat.Builder builder) {
        Notification notification = builder.build();
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(mContext);
        notificationManager.notify(1, notification);
    }

}
