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
import android.util.Log;
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

        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Class Routine");
        }




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
