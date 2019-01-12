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

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.assaduzzaman.teachersdiary.Notification.NotificationReceiver;
import me.assaduzzaman.teachersdiary.R;

import static java.util.Calendar.AM_PM;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout  TimeChooser;
    Switch notifySwitch;
    TextView time;
    SharedPreferences sharedPreferences;
    SharedPreferences retrivePreferences;
    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSetting);
        TimeChooser=findViewById(R.id.set_time);
        time=findViewById(R.id.settingTime);

        notifySwitch=findViewById(R.id.switchNotification);


        //get sharedPreferences time information
        retrivePreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        SharedPreferences.Editor editor=retrivePreferences.edit();
        boolean check=sharedPreferences.getBoolean("notification",true);

        if (check)
        {
            notifySwitch.setChecked(true);
        }
        else
        {
            notifySwitch.setChecked(false);
        }

        String preferencesTime=retrivePreferences.getString("pickerTime","0");
        time.setText(preferencesTime);





        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }






        notifySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor=sharedPreferences.edit();

            if (isChecked)
            {

                editor.putBoolean("notification",true);
                editor.apply();
            }
            else
            {
                editor.putBoolean("notification",false);
                editor.apply();

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




                        // formating time
                        String status = "AM";

                        if(i > 11)
                        {
                            status = "PM";
                        }
                        // Initialize a new variable to hold 12 hour format hour value
                        int hour_of_12_hour_format;

                        if(i > 11){
                            hour_of_12_hour_format = i - 12;
                        }
                        else {
                            hour_of_12_hour_format = i;
                        }

                        String finalTime=hour_of_12_hour_format + " : " + i1 + " : " + status;
                        time.setText(finalTime);

                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("pickerTime",finalTime);
                        editor.apply();

                        Toast.makeText(SettingsActivity.this, "Notification set to "+finalTime, Toast.LENGTH_SHORT).show();


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
