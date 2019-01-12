package me.assaduzzaman.teachersdiary.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.assaduzzaman.teachersdiary.Activity.CreateNoteActivity;
import me.assaduzzaman.teachersdiary.Activity.SplashActivity;
import me.assaduzzaman.teachersdiary.LocalDatabase.Config;
import me.assaduzzaman.teachersdiary.LocalDatabase.DatabaseHelper;
import me.assaduzzaman.teachersdiary.MainActivity;
import me.assaduzzaman.teachersdiary.R;
import me.assaduzzaman.teachersdiary.model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    Context context;
    ArrayList<Note> noteList;


    public NoteAdapter(Context context, ArrayList<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }


    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.note_row, viewGroup, false);


        return new NoteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteAdapter.MyViewHolder myViewHolder, final int i) {


        myViewHolder.noteText.setText(noteList.get(i).getNoteDetails());
        myViewHolder.noteDate.setText(noteList.get(i).getDate());
        myViewHolder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                setCustomAlart(noteList.get(i).getNoteID());


                return false;
            }
        });

        myViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked"+noteList.get(i).getNoteID(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, CreateNoteActivity.class);
                intent.putExtra("details",noteList.get(i).getNoteDetails());
                intent.putExtra("update","update");
                context.startActivity(intent);



            }
        });





    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView noteDate,noteText;
        LinearLayout layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            layout=itemView.findViewById(R.id.row);
            noteDate=itemView.findViewById(R.id.noteDate);
            noteText=itemView.findViewById(R.id.noteText);

        }


    }

    private void setCustomAlart(final String i) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Want to delete the note???");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

                        databaseHelper.deleteNote(context, i);
                        NoteAdapter.this.refreshAdapter(getNotetList(context));



                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    public void refreshAdapter(ArrayList<Note> newList)
    {
        noteList.clear();
        noteList.addAll(newList);
        notifyDataSetChanged();
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
