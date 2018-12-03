package me.assaduzzaman.teachersdiary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.assaduzzaman.teachersdiary.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashActivity.this,LoginActivity.class));

       // setContentView(R.layout.activity_splash);
    }
}
