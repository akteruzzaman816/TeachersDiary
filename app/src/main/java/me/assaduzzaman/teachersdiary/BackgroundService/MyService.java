package me.assaduzzaman.teachersdiary.BackgroundService;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.ScheduleTime;

public class MyService extends Service {
    private final String CHANNEL_ID="Teacher's Diary";
    private final int NOTIFICATION_ID=001;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("Thread-Service", String.valueOf(Thread.currentThread().getId()));
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        checkSchedule();
                    }
                },0,1000);

            }
        }).start();



        return START_STICKY;
    }

    private void checkSchedule() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sirCode=preferences.getString("code","0");


        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE,10);
        Date date = calendar.getTime();

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
        String currentTime=simpleDateFormat.format(calendar.getTime());
        Log.e("Hello",currentTime);
        String currentDay=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());

        ArrayList<ScheduleTime>timeList=getRoutinetData(sirCode,currentDay);



//        ArrayList<String> timeList=new ArrayList<>();
//        timeList.add("04:37:00");
//        timeList.add("04:35:00");
//        timeList.add("04:36:00");
//        timeList.add("04:34:00");
//
//        Log.e("hello",currentTime);

        for (int i=0;i<timeList.size();i++)
        {

            Log.e("task2",timeList.get(i).getCourseName());
            if (timeList.get(i).equals(currentTime))

            {
                NotificationCompat.Builder builder= new NotificationCompat.Builder(MyService.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Teachers's Diary")
                        .setContentText("You Have to take "+timeList.get(i).getCourseName()+" after 10 minutes")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        }




    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private ArrayList<ScheduleTime> getRoutinetData(String sirCode, String day) {



        DatabaseHelper databaseHelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        ArrayList<ScheduleTime> list = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from "+ Config.TABLE_ROUTINE+" where "
                    +Config.COLUMN_ROUTINE_DAY+"=? and "+Config.COLUMN_TEACHER_CODE+"=?" ,new String [] {day,String.valueOf(sirCode)});

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                list.add(new ScheduleTime(

                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_TIME)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_NAME))
                ));
            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }


        return list;


    }

}
