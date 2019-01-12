package me.assaduzzaman.teachersdiary.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.assaduzzaman.teachersdiary.Adapter.NoteAdapter;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Note;

public class CreateNoteActivity extends AppCompatActivity {

    EditText note;
    Button saveNote,exitButton;
    TextView title,noteDate;
    Calendar c;
    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);





        note=findViewById(R.id.noteDetails);
        saveNote=findViewById(R.id.saveNote);
        exitButton=findViewById(R.id.exit);
        title=findViewById(R.id.titleNote);
        noteDate=findViewById(R.id.noteTime);


        String details=getIntent().getStringExtra("details");
        note.setText(details);
        String noteTitle=getIntent().getStringExtra("title");
        if (noteTitle==null)
        {
            title.setText("Create Note");
        }
        else
        {
            title.setText(noteTitle);
        }


        //set the current note
        c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy , HH:mm:ss a");
        final String  currentDate=dateFormat.format(c.getTime());
        noteDate.setText(currentDate);






        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (note.getText().length()>0)
                {


                    DatabaseHelper databaseHelper = new DatabaseHelper(CreateNoteActivity.this);
                    SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
                    String noteID=getIntent().getStringExtra("id");


                    if (getIntent().getStringExtra("update")!=null)
                    {
                        databaseHelper.updateNote(new Note(note.getText().toString(),currentDate),noteID,sqLiteDatabase);
                    }else
                    {
                        databaseHelper.saveNote(new Note(note.getText().toString(),currentDate),sqLiteDatabase);
                    }


                    Intent intent=new Intent(CreateNoteActivity.this,NoteActivity.class);
                    startActivity(intent);
                    finish();
                    //overridePendingTransition(R.anim.slide_in_down,R.anim.slide_in_up);

                }else
                {
                    Toast.makeText(CreateNoteActivity.this, "Note is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            onBackPressed();

            }
        });

    }
}


