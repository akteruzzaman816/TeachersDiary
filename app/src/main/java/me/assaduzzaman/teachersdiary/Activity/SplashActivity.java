package me.assaduzzaman.teachersdiary.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import me.assaduzzaman.teachersdiary.BaseUrl;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.Network.NetworkStatus;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class SplashActivity extends AppCompatActivity {

    JsonArrayRequest arrayRequest;
    RequestQueue requestQueue;
    ArrayList<Routine> allRoutine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            // function call for get the JSON Data..........
            if (new NetworkStatus().checkNetworkConnection(SplashActivity.this))
            {
                getData();
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }






       // setContentView(R.layout.activity_splash);
    }


    public void getData() {


        if (new NetworkStatus().checkNetworkConnection(SplashActivity.this)) {


            allRoutine = new ArrayList<>();

            arrayRequest = new JsonArrayRequest(new BaseUrl().getRoutineUrl(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("Hello","sdfsdfs");

                    DatabaseHelper databaseHelper = new DatabaseHelper(SplashActivity.this);
                    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
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

            requestQueue= Volley.newRequestQueue(SplashActivity.this);
            requestQueue.add(arrayRequest);

        }else
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }








    }
}
