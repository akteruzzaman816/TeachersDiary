package me.assaduzzaman.teachersdiary.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import me.assaduzzaman.teachersdiary.Adapter.ViewPagerAdapter;
import me.assaduzzaman.teachersdiary.BaseUrl;
import me.assaduzzaman.teachersdiary.Fragment.FriFragment;
import me.assaduzzaman.teachersdiary.Fragment.MonFragment;
import me.assaduzzaman.teachersdiary.Fragment.SatFragment;
import me.assaduzzaman.teachersdiary.Fragment.SunFragment;
import me.assaduzzaman.teachersdiary.Fragment.TuesFragment;
import me.assaduzzaman.teachersdiary.Fragment.WedFragment;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.Network.NetworkStatus;
import me.assaduzzaman.teachersdiary.Notification.NotificationReceiver;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class RoutineActivity extends AppCompatActivity {

    JsonArrayRequest arrayRequest;
    RequestQueue requestQueue;
    ArrayList<Routine> allRoutine;



    //Constructor......
    public RoutineActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        //initialize the items....
        TabLayout tabLayout=findViewById(R.id.tablayout);
        Toolbar toolbar = findViewById(R.id.toolbarRoutine);
        ViewPager viewPager=findViewById(R.id.view_pager);


        //Notifications................................
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,17);
        calendar.set(Calendar.MINUTE,17);
        calendar.set(Calendar.SECOND,10);
        Intent intent=new Intent(getApplicationContext(), NotificationReceiver.class);

        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100
        ,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                alarmManager.INTERVAL_DAY,pendingIntent);




        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Class Routine");
        }


            getData();

        setviewpager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.SATURDAY == dayOfWeek) {
            viewPager.setCurrentItem(0, true);

        } else if (Calendar.SUNDAY == dayOfWeek) {
            viewPager.setCurrentItem(1, true);
        } else if (Calendar.MONDAY == dayOfWeek) {
            viewPager.setCurrentItem(2, true);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            viewPager.setCurrentItem(3, true);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            viewPager.setCurrentItem(4, true);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            viewPager.setCurrentItem(5, true);
        } else if (Calendar.FRIDAY == dayOfWeek) {
            viewPager.setCurrentItem(6, true);
        }

    }


    public void getData() {

        if (new NetworkStatus().checkNetworkConnection(RoutineActivity.this)) {

            allRoutine = new ArrayList<>();

            arrayRequest = new JsonArrayRequest(new BaseUrl().getRoutineUrl(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    DatabaseHelper databaseHelper = new DatabaseHelper(RoutineActivity.this);
                    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                    databaseHelper.deleteData(RoutineActivity.this);

                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            if (response.length() > 0) {

                                jsonObject = response.getJSONObject(i);
                                Routine routine = new Routine();
                                routine.setRoutineID(jsonObject.getInt("routine_id"));
                                routine.setTeacherCode(jsonObject.getString("teacher_code"));
                                routine.setCourseName(jsonObject.getString("course_name"));
                                routine.setRoutineDay(jsonObject.getString("routine_day"));
                                routine.setRoutineFaculty(jsonObject.getString("routine_faculty"));
                                routine.setRoutineSemester(jsonObject.getString("routine_semester"));
                                routine.setRoutineSection(jsonObject.getString("routine_section"));
                                routine.setRoutineTime(jsonObject.getString("routine_time"));
                                routine.setRoutineRoom(jsonObject.getString("routine_room"));
                                routine.setRoutineBatch(jsonObject.getString("routine_batch"));
                                routine.setCourseCode(jsonObject.getString("course_code"));
                                routine.setRoutineStatus(jsonObject.getInt("routine_status"));


                                //saving data........
                                databaseHelper.saveToLocalDatabase(routine,sqLiteDatabase);



//                                routine.setRoutineID(allRoutine.get(0).getRoutineID());
//                                routine.setTeacherCode(allRoutine.get(1).getTeacherCode());
//                                routine.setCourseName(allRoutine.get(2).getCourseName());
//                                routine.setRoutineDay(allRoutine.get(3).getRoutineDay());
//                                routine.setRoutineFaculty(allRoutine.get(4).getRoutineFaculty());
//                                routine.setRoutineSemester(allRoutine.get(5).getRoutineSemester());
//                                routine.setRoutineSection(allRoutine.get(6).getRoutineSection());
//                                routine.setRoutineTime(allRoutine.get(7).getRoutineTime());
//                                routine.setRoutineRoom(allRoutine.get(8).getRoutineRoom());
//                                routine.setRoutineBatch(allRoutine.get(9).getRoutineBatch());
//                                routine.setCourseCode(allRoutine.get(10).getCourseCode());
//                                routine.setRoutineStatus(allRoutine.get(11).getRoutineStatus());


                                allRoutine.add(routine);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue= Volley.newRequestQueue(RoutineActivity.this);
            requestQueue.add(arrayRequest);

        }else
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }






    }





    private void setviewpager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addfragment(new SatFragment(),"Saturday");
        viewPagerAdapter.addfragment(new SunFragment(),"Sunday");
        viewPagerAdapter.addfragment(new MonFragment(),"Monday");
        viewPagerAdapter.addfragment(new TuesFragment(),"Tuesday");
        viewPagerAdapter.addfragment(new WedFragment(),"Wednesday");
        viewPagerAdapter.addfragment(new TuesFragment(),"Thursday");
        viewPagerAdapter.addfragment(new FriFragment(),"Friday");
        viewPager.setAdapter(viewPagerAdapter);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            RoutineActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
