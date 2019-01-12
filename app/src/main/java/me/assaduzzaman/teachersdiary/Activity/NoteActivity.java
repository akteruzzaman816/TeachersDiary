package me.assaduzzaman.teachersdiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import me.assaduzzaman.teachersdiary.Adapter.NoteAdapter;
import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Note;

public class NoteActivity extends AppCompatActivity {


    ArrayList<Note> dataList = new ArrayList<>();
    NoteAdapter noteAdapter;
    RecyclerView recyclerView;

    FloatingActionButton floatingActionButton;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = findViewById(R.id.toolbarNote);
        frame = findViewById(R.id.frame);

        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Notes");
        }


        recyclerView=findViewById(R.id.note);

        // floating Action Button.....
        floatingActionButton = findViewById(R.id.fab_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
;
                Intent intent=new Intent(NoteActivity.this,CreateNoteActivity.class);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.slide_in_up,R.anim.slide_in_down);
            }
        });


        dataList=getNotetList(NoteActivity.this);
        if (dataList.size()>0)
        {
           // recyclerView.setVisibility(View.VISIBLE);
            noteAdapter = new NoteAdapter(NoteActivity.this,dataList);
            recyclerView.setLayoutManager(new LinearLayoutManager(NoteActivity.this));
            recyclerView.setAdapter(noteAdapter);
           // noteAdapter.refreshAdapter(dataList);







        }
        else
        {

            recyclerView.setVisibility(View.GONE);
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            NoteActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }




    public ArrayList<Note> getNotetList(Context context) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        ArrayList<Note> notelist = new ArrayList<>();

        try {

            Cursor cursor = db.rawQuery("select * from " + Config.TABLE_NOTE, null);

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                notelist.add(new Note(
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTE_ID)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTE_DETAILS)),
                        cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTE_DATE))
                ));

            }

            db.close();

        } catch (SQLiteException e) {
            db.close();
        }

        return notelist;


    }


}

