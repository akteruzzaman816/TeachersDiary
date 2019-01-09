package me.assaduzzaman.teachersdiary.BackgroundService;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import me.assaduzzaman.teachersdiary.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {

  //  private boolean jobCanceled=false;

    private final String CHANNEL_ID="Teacher's Diary";
    private final int NOTIFICATION_ID=001;


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("Job-Thread-Service", String.valueOf(Thread.currentThread().getId()));
                Timer timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        checkSchedule(jobParameters);
                    }
                },0,1000);
                //jobFinished(jobParameters,false);

            }

        }).start();



        return true;
    }


    private void checkSchedule(JobParameters jobParameters) {

        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
        String currentTime=simpleDateFormat.format(calendar.getTime());

        ArrayList<String> timeList=new ArrayList<>();
        timeList.add("10:26:33");
        timeList.add("10:27:33");
        timeList.add("10:28:33");
        timeList.add("10:30:00");

        Log.e("hello",currentTime);

        for (int i=0;i<timeList.size();i++)
        {

            if (timeList.get(i).equals(currentTime))

            {
                NotificationCompat.Builder builder= new NotificationCompat.Builder(MyJobService.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Teachers's Diary")
                        .setContentText("Check out you class Schedule")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }
        }




    }


    @Override
    public boolean onStopJob(JobParameters jobParameters) {

     //   jobCanceled=true;
        return true;
    }
}
