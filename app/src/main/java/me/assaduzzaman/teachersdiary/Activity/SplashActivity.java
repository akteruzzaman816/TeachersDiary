package me.assaduzzaman.teachersdiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.Collections;
import java.util.Comparator;

import me.assaduzzaman.teachersdiary.BaseUrl;
import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.Network.NetworkStatus;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class SplashActivity extends AppCompatActivity {

     boolean isFound;

    JsonArrayRequest arrayRequest;
    RequestQueue requestQueue;
    ArrayList<Routine> allRoutine;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences  sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("update","noUpdate");

            // function call for get the JSON Data..........
            if (new NetworkStatus().checkNetworkConnection(SplashActivity.this))
            {
                getData();

            }
            else
            {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }






       // setContentView(R.layout.activity_splash);
    }


    public void getData() {
         databaseHelper = new DatabaseHelper(SplashActivity.this);
         sqLiteDatabase = databaseHelper.getWritableDatabase();



        if (!databaseHelper.checkDatabase(sqLiteDatabase))
        {
            SharedPreferences  sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("data","empty");
            editor.apply();
            Log.e("notifySplash","empty");
        }
        else {
            SharedPreferences  sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("data","full");
            editor.apply();
            Log.e("notifySplash","full");
        }

        if (new NetworkStatus().checkNetworkConnection(SplashActivity.this)) {


            allRoutine = new ArrayList<>();

            arrayRequest = new JsonArrayRequest(new BaseUrl().getRoutineUrl(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {


                    databaseHelper.deleteData(SplashActivity.this);

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

                    compareChanges(allRoutine);

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue= Volley.newRequestQueue(SplashActivity.this);
            requestQueue.add(arrayRequest);

        }else
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }



    }

    private void compareChanges(ArrayList<Routine> routine) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        String sirCode=preferences.getString("code","0");

        ArrayList<Routine> localData=new ArrayList<>();
        ArrayList<Routine> onlineData=new ArrayList<>();
        localData.clear();
        onlineData.clear();

        localData=getRoutinetList(SplashActivity.this);


        //online data sorting
        for (int i=0;i<routine.size();i++)
        {
            if (routine.get(i).getTeacherCode().equals(sirCode))
            {
                onlineData.add(routine.get(i));
            }
        }
        Log.e("dataLocalSIZE", String.valueOf(localData.size()));
        Log.e("dataOnlineSIZE", String.valueOf(onlineData.size()));


         // Main compare loop
        int loop=0;
        int count=0;
        for ( loop = 0; loop < localData.size(); loop++) {


            Log.e("dataLocal",localData.get(loop).getRoutineTime());
            Log.e("dataOnline",onlineData.get(loop).getRoutineTime());

            if (localData.get(loop).getRoutineID()==onlineData.get(loop).getRoutineID())
            {
                if ((localData.get(loop).getRoutineTime().equals(onlineData.get(loop).getRoutineTime()))
                && (localData.get(loop).getRoutineDay().equals(onlineData.get(loop).getRoutineDay()))
                && (localData.get(loop).getRoutineSemester().equals(onlineData.get(loop).getRoutineSemester()))        ) {
                    count++;

                }else
                {
                    isFound=false;
                    break;
                }

            }


        }

        if (count==loop)
        {
            SharedPreferences  sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("update","noUpdate");

            editor.apply();
            Log.e("SplashUpdate","NoUpdate");

            for (int i=0;i<routine.size();i++)
            {
                databaseHelper.saveToLocalDatabase(routine.get(i),sqLiteDatabase);
            }



        }
        else {
            SharedPreferences  sharedPreferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("update","update");

            for (int i=0;i<routine.size();i++)
            {
                databaseHelper.saveToLocalDatabase(routine.get(i),sqLiteDatabase);
            }

            Log.e("SplashUpdate","Update");
        }


        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();


    }

    public  ArrayList<Routine> getRoutinetList(Context context) {

        DatabaseHelper databaseHelper=new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        String sirCode=preferences.getString("code","0");

        ArrayList<Routine> routinelist = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from "+ Config.TABLE_ROUTINE+" where "
                    +Config.COLUMN_TEACHER_CODE+"=?" ,new String [] {sirCode});

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                routinelist.add(new Routine(
                        cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ROUTINE_ID)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_DAY)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_SEMESTER)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_TIME))

                ));
            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }
        Log.e("data3", String.valueOf(routinelist.size()));

        return routinelist;
    }
}
