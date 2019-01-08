package me.assaduzzaman.teachersdiary.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Note;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = findViewById(R.id.toolbarNote);
        frameLayout=findViewById(R.id.frameNote);

        //setting the toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Notes");
        }

        // floating Action Button.....
        floatingActionButton=findViewById(R.id.fab_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent= new Intent(NoteActivity.this,CreateNoteActivity.class);
                startActivity(intent);

            }
        });


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            NoteActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
