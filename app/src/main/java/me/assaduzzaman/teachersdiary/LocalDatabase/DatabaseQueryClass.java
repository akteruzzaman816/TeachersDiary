package me.assaduzzaman.teachersdiary.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import me.assaduzzaman.teachersdiary.model.Routine;

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;

    }

    public long insertStudent(Routine routine) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_ROUTINE_ID, routine.getRoutineID());
        contentValues.put(Config.COLUMN_COURSE_NAME, routine.getCourseName());
        contentValues.put(Config.COLUMN_ROUTINE_DAY, routine.getRoutineDay());
        contentValues.put(Config.COLUMN_ROUTINE_FACULTY, routine.getRoutineFaculty());
        contentValues.put(Config.COLUMN_ROUTINE_SEMESTER, routine.getRoutineSemester());
        contentValues.put(Config.COLUMN_ROUTINE_SECTION, routine.getRoutineSection());
        contentValues.put(Config.COLUMN_ROUTINE_TIME, routine.getRoutineTime());
        contentValues.put(Config.COLUMN_ROUTINE_ROOM, routine.getRoutineRoom());
        contentValues.put(Config.COLUMN_ROUTINE_BATCH, routine.getRoutineBatch());
        contentValues.put(Config.COLUMN_COURSE_CODE, routine.getCourseCode());
        contentValues.put(Config.COLUMN_COURSE_STATUS, routine.getRoutineStatus());


        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_ROUTINE, null, contentValues);
        } catch (SQLiteException e) {
            Log.e("Exception: ", e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public ArrayList<Routine> getAllData() {

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_ROUTINE, null, null, null, null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if (cursor != null)
                if (cursor.moveToFirst()) {
                    ArrayList<Routine> routineList = new ArrayList<>();
                    do {
                        String semester = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_SEMESTER));
                        String section = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_SECTION));
                        String batch = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_BATCH));
                        String subCode = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE));
                        String time = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_TIME));
                        String room = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_ROOM));
                        String subName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_COURSE_CODE));
                        String day = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ROUTINE_DAY));

                        routineList.add(new Routine(semester,batch,section,subName,day,time,room,subCode));
                    } while (cursor.moveToNext());

                    return routineList;
                }
        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return (ArrayList<Routine>) Collections.EMPTY_LIST;
    }
}