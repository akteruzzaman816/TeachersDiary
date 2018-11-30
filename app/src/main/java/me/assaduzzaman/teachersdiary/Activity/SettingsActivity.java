package me.assaduzzaman.teachersdiary.Activity;

import android.app.TimePickerDialog;
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

import java.util.Calendar;

import me.assaduzzaman.teachersdiary.R;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout  TimeChooser;
    Switch notifySwitch;
    TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSetting);
        TimeChooser=findViewById(R.id.set_time);
        time=findViewById(R.id.settingTime);

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
                mTimePicker = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });





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
