package me.assaduzzaman.teachersdiary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import me.assaduzzaman.teachersdiary.R;

public class CreateNoteActivity extends AppCompatActivity {

    TextView noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteText=findViewById(R.id.noteText);

        noteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteText.setCursorVisible(true);
                noteText.setFocusableInTouchMode(true);
                noteText.setInputType(InputType.TYPE_CLASS_TEXT);
                noteText.requestFocus(); //to trigger the soft input
            }
        });


    }
}
