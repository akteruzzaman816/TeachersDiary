package me.assaduzzaman.teachersdiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.assaduzzaman.teachersdiary.BaseUrl;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.Network.NetworkStatus;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class LoginActivity extends AppCompatActivity {

    JsonArrayRequest arrayRequest;
    RequestQueue requestQueue;
    ArrayList<Routine> allRoutine;



    public static final String MY_PREFERENCES = "me.assaduzzaman.teachersdiary.Activity" ;


    SharedPreferences sharedPreferences;

    RelativeLayout layout;
    EditText email,password;
    Button loginButton;
    ProgressBar progressBar;
    Boolean check;


    BaseUrl baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // initialize the items
        email=findViewById(R.id.edit_email);
        password=findViewById(R.id.edit_password);
        loginButton=findViewById(R.id.button_login);
        progressBar=findViewById(R.id.progressBar);
        layout=findViewById(R.id.activity_login);


        // initialize the base url..........
        baseUrl=new BaseUrl();


        //check for existing SharedPreferences
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        check=sharedPreferences.getBoolean("firstTime",false);

        if (check)
        {

                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

        }


        //action for login Button


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                if (e.isEmpty())
                {
                    email.setError("Please enter email");

                }
                else if (p.isEmpty())
                {
                    password.setError("please enter password");
                }
                else {
                    login(e,p);
                }

            }
        });



    }


    private void login(final String e, final String p) {
        if (new NetworkStatus().checkNetworkConnection(LoginActivity.this))
        {

            progressBar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);

            StringRequest stringRequest=new StringRequest(Request.Method.POST, baseUrl.
                    getLoginUrl().concat("?email=").concat(e).concat("&password=").concat(p),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String success=jsonObject.getString("response");



                                if(success.equals("ok"))
                                {
                                    // function call for get the JSON Data..........

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            getData();

                                        }
                                    }).start();


                                    String phone=jsonObject.getString("phone");
                                    String designation=jsonObject.getString("designation");
                                    String name=jsonObject.getString("name");
                                    String teacherCode=jsonObject.getString("code");



                                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();

                                    editor.putString("email",e);
                                    editor.putString("phone",phone);
                                    editor.putString("name",name);
                                    editor.putString("code",teacherCode);
                                    editor.putString("designation",designation);
                                    editor.putBoolean("firstTime",true);
                                    editor.apply();






                                }
                                else if (success.equals("failed to load"))
                                {
                                    progressBar.setVisibility(View.GONE);
                                    loginButton.setVisibility(View.VISIBLE);
                                    Snackbar.make(layout, "Failed To Login....", Snackbar.LENGTH_SHORT)
                                            .show();
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                                Snackbar.make(layout, "Failed To Login...."+e1, Snackbar.LENGTH_SHORT)
                                        .show();
                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loginButton.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(layout, "Failed To Login...."+error, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    })
            {

            };

            RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(stringRequest);

        }else {

            Toast.makeText(this, "                     No Internet \n Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }


    public void getData() {


        if (new NetworkStatus().checkNetworkConnection(LoginActivity.this)) {


            allRoutine = new ArrayList<>();

            arrayRequest = new JsonArrayRequest(new BaseUrl().getRoutineUrl(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.e("Hello","sdfsdfs");

                    DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
                    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                    databaseHelper.deleteData(LoginActivity.this);

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

                    progressBar.setVisibility(View.GONE);
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    loginButton.setVisibility(View.VISIBLE);

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue= Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(arrayRequest);

        }else
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }








    }



}
