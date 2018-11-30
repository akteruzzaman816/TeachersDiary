package me.assaduzzaman.teachersdiary.Notification;

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

        Intent destinationIntent=new Intent(context, RoutineActivity.class);
        destinationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,destinationIntent
        ,PendingIntent.FLAG_UPDATE_CURRENT);


        //for Notification sound........
        Uri Sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder builder= new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Teachers's Diary")
                .setContentText("Check out you class Schedule")
                .setAutoCancel(true)
                .setSound(Sound);

        notificationManager.notify(100,builder.build());

    }
}
