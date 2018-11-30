package me.assaduzzaman.teachersdiary.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.R;

public class ProfileActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    TextView profileName,
            profileDesignation,
            profileEmail,
            profileFacultyName,
            profileCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        ViewPager viewPager=findViewById(R.id.view_pager);

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


        // getting the information from the sharepreference...................
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        final String name=sharedPreferences.getString("name","0");
        final String email=sharedPreferences.getString("email","0");
        final String code=sharedPreferences.getString("code","0");

        // setting the information to the view....
        profileName.setText(name);
        profileEmail.setText(email);
        profileCode.setText(code);







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
