package me.assaduzzaman.teachersdiary.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import me.assaduzzaman.teachersdiary.R;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    TextView profileName,
            profileDesignation,
            profileEmail,
            profilePhone,
            profileFacultyName,
            profileCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.profile_toolbar);

        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }


        // setting the view with id............
        profileName=findViewById(R.id.profileName);
        profileDesignation=findViewById(R.id.profileDesignation);
        profileCode=findViewById(R.id.profileCode);
        profileEmail=findViewById(R.id.profileEmail);
        profileFacultyName=findViewById(R.id.profileFaculty);
        profilePhone=findViewById(R.id.profilePhone);


        // getting the information from the sharedPreferences ...................
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        final String name=sharedPreferences.getString("name","0");
        final String email=sharedPreferences.getString("email","0");
        final String code=sharedPreferences.getString("code","0");
        final String phone=sharedPreferences.getString("phone","0");
        final String designation=sharedPreferences.getString("designation","0");

        // setting the information to the view....
        profileName.setText(name);
        profileEmail.setText(email);
        profileCode.setText(code);
        profileDesignation.setText(designation);
        profilePhone.setText(phone);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            ProfileActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
