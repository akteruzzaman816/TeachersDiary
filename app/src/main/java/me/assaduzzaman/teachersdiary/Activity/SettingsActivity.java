package me.assaduzzaman.teachersdiary.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import me.assaduzzaman.teachersdiary.Notification.NotificationReceiver;
import me.assaduzzaman.teachersdiary.R;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout  TimeChooser;
    Switch notifySwitch;
    TextView time;
    SharedPreferences sharedPreferences;
    SharedPreferences retrivePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSetting);
        TimeChooser=findViewById(R.id.set_time);
        time=findViewById(R.id.settingTime);

        //get sharedPreferences time information
        retrivePreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        int hour=retrivePreferences.getInt("notifyHour",0);
        int minute=retrivePreferences.getInt("notifyMinute",0);
        time.setText(String.valueOf(hour)+"."+String.valueOf(minute));





        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }





        notifySwitch=findViewById(R.id.switchNotification);

        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (b)
            {
               TimeChooser.setVisibility(View.VISIBLE);
            }
            else
            {
               TimeChooser.setVisibility(View.GONE);
            }




            }
        });



        TimeChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker=new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putInt("notifyHour",i);
                        editor.putInt("notifyMinute",i1);
                        editor.apply();

                        time.setText( i + ":" + i1);

                        setNotifications(i,i1);

                    }
                },hour,minute,false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });





    }

    private void setNotifications(int hour,int minute) {


        //set up for Notifications................................
        Toast.makeText(this,  "Notification set to "+String.valueOf(hour)+"."+String.valueOf(minute), Toast.LENGTH_SHORT).show();
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        Intent intent=new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100
                ,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                alarmManager.INTERVAL_DAY,pendingIntent);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            SettingsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
