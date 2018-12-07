package me.assaduzzaman.teachersdiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.assaduzzaman.teachersdiary.BaseUrl;
import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.R;

public class LoginActivity extends AppCompatActivity {
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
                                String name=jsonObject.getString("name");
                                String teacherCode=jsonObject.getString("code");

                                sharedPreferences= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor editor=sharedPreferences.edit();

                                editor.putString("email",e);
                                editor.putString("name",name);
                                editor.putString("code",teacherCode);
                                editor.putBoolean("firstTime",true);
                                editor.apply();

                                progressBar.setVisibility(View.GONE);
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                loginButton.setVisibility(View.VISIBLE);


                            }
                            else if (success.equals("failed to load"))
                            {
                                progressBar.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                                Snackbar.make(layout, "Failed....", Snackbar.LENGTH_SHORT)
                                        .show();
                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            loginButton.setVisibility(View.VISIBLE);
                            Snackbar.make(layout, "Failed...."+e1, Snackbar.LENGTH_SHORT)
                                    .show();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginButton.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(layout, "Failed...."+error, Snackbar.LENGTH_SHORT)
                                .show();
                    }
                })
        {

        };

        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
