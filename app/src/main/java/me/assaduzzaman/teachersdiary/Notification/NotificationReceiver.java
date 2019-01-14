package me.assaduzzaman.teachersdiary.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import me.assaduzzaman.teachersdiary.Activity.RoutineActivity;
import me.assaduzzaman.teachersdiary.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 100;
        String channelId = "channel-01";
        String channelName = "Channel Name";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent destinationIntent=new Intent(context, RoutineActivity.class);
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,destinationIntent
        ,PendingIntent.FLAG_UPDATE_CURRENT);


        //for Notification sound........
        Uri Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,channelId)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Teachers's Diary")
                .setContentText("Be Ready for your Next Classes")
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(Sound)
                .setAutoCancel(true);

        notificationManager.notify(notificationId,builder.build());

    }
}
