package me.assaduzzaman.teachersdiary.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.assaduzzaman.teachersdiary.Adapter.CustomAdapter;
import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Routine;

public class FriFragment extends Fragment {
    ArrayList<Routine> dataList=new ArrayList<>();
    CustomAdapter customAdapter;
    RecyclerView recyclerView;

    View catLayout;


    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fri,container,false);


        catLayout=v.findViewById(R.id.fri_cat);

        recyclerView=v.findViewById(R.id.recyclerView_fri);
        dataList=getRoutinetList(getActivity());

        if (dataList.size()>0)
        {
            catLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            customAdapter=new CustomAdapter(getActivity(),dataList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(customAdapter);
        }
        else
        {
            catLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }




        return v;

    }

    public  ArrayList<Routine> getRoutinetList(Context context) {

        DatabaseHelper databaseHelper=new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sirCode=preferences.getString("code","0");

        ArrayList<Routine> routinelist = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from "+ Config.TABLE_ROUTINE+" where "
                    +Config.COLUMN_ROUTINE_DAY+"=? and "+Config.COLUMN_TEACHER_CODE+"=?" ,new String [] {"Friday",sirCode});

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                routinelist.add(new Routine(
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_TEACHER_CODE)),
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
        return routinelist;
    }


}
