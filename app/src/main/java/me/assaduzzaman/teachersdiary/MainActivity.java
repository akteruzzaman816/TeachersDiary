package me.assaduzzaman.teachersdiary;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import me.assaduzzaman.teachersdiary.Activity.ProfileActivity;
import me.assaduzzaman.teachersdiary.Activity.RoutineActivity;
import me.assaduzzaman.teachersdiary.Activity.SettingsActivity;
import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.model.Routine;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    CardView profileCard,noteCard,routineCard,attendanceCard,settingCard,aboutCard;

    TextView dayDate,classStart,classEnd,totalClass;

    LinearLayout contentDashboard;
    TextView scheduleText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
      //  TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


      //
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final String name=sharedPreferences.getString("name","0");


        // reference  for Card view............................
        profileCard=findViewById(R.id.profileCard);
        noteCard=findViewById(R.id.noteCard);
        routineCard=findViewById(R.id.routineCard);
        attendanceCard=findViewById(R.id.attendanceCard);
        settingCard=findViewById(R.id.settingCard);
        aboutCard=findViewById(R.id.aboutCard);


        // reference for the Text view...........................
        dayDate=findViewById(R.id.dayDate);
        classStart=findViewById(R.id.classStart);
        classEnd=findViewById(R.id.classEnd);
        totalClass=findViewById(R.id.totalClass);


        // Dashboard visibility reference.............
        contentDashboard=findViewById(R.id.contentDashboard);
        scheduleText=findViewById(R.id.scheduleText);


        getDashBoardInfo();





        //click event for the cards..................
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCustomAlart();

            }
        });


        routineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RoutineActivity.class);
                startActivity(intent);
            }
        });


        attendanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCustomAlart();
            }
        });


        settingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCustomAlart();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    private void getDashBoardInfo() {

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String sirCode=preferences.getString("code","0");

        Calendar c = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate=dateFormat.format(c.getTime());



        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        if (Calendar.SATURDAY == dayOfWeek) {



            dayDate.setText("Saturday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Saturday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }




        } else if (Calendar.SUNDAY == dayOfWeek) {

            dayDate.setText("Sunday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Sunday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }




        } else if (Calendar.MONDAY == dayOfWeek) {

            dayDate.setText("Monday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Monday");
            if (data.size()>0)
            {

                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }


        } else if (Calendar.TUESDAY == dayOfWeek) {

            dayDate.setText("Tuesday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Tuesday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }


        } else if (Calendar.WEDNESDAY == dayOfWeek) {

            dayDate.setText("Wednesday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Wednesday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }


        } else if (Calendar.THURSDAY == dayOfWeek) {

            dayDate.setText("Thursday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Thursday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }


        } else if (Calendar.FRIDAY == dayOfWeek) {

            dayDate.setText("Friday"+" \n "+currentDate);

            ArrayList<Routine> data= getRoutinetData(sirCode,"Friday");
            if (data.size()>0)
            {
                classStart.setText(data.get(0).getRoutineTime());
                classEnd.setText(data.get(data.size()-1).getRoutineTime());
                totalClass.setText(String.valueOf(data.size()));
            }
            else {
                contentDashboard.setVisibility(View.GONE);
                scheduleText.setVisibility(View.VISIBLE);

            }



        }




    }

    private ArrayList<Routine> getRoutinetData(String sirCode, String day) {

        DatabaseHelper databaseHelper=new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        ArrayList<Routine> routinelist = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from "+ Config.TABLE_ROUTINE+" where "
                    +Config.COLUMN_ROUTINE_DAY+"=? and "+Config.COLUMN_TEACHER_CODE+"=?" ,new String [] {day,String.valueOf(sirCode)});

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                routinelist.add(new Routine(
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_SEMESTER)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_BATCH)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_SECTION)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_NAME)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_DAY)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_TIME)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_ROOM)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE))
                ));
            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }
        Log.e("data3", String.valueOf(routinelist.size()));

        Collections.sort(routinelist, new Comparator<Routine>() {
            @Override
            public int compare(Routine routine, Routine t1) {
                return routine.getRoutineTime().compareTo(t1.getRoutineTime());
            }
        });
        return routinelist;


    }

    private void setCustomAlart() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_alart, null);

        TextView title = (TextView) view.findViewById(R.id.title);



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setView(view);
        builder.show();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.finish();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.actionLogout) {
            SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.putBoolean("firstTime",false);
            editor.apply();
            finish();

        }
        if(id== R.id.actionSetting)
        {
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(MainActivity.this, RoutineActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.setting) {
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
