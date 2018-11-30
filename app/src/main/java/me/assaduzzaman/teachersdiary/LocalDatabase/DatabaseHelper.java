package me.assaduzzaman.teachersdiary.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import me.assaduzzaman.teachersdiary.model.Routine;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.TABLE_ROUTINE + "("
                + Config.COLUMN_ROUTINE_ID+ " INT, "
                + Config.COLUMN_TEACHER_CODE + " TEXT, "
                + Config.COLUMN_COURSE_NAME + " TEXT, "
                + Config.COLUMN_ROUTINE_DAY + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_FACULTY + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_SEMESTER + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_SECTION + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_TIME + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_ROOM + " TEXT, " //nullable
                + Config.COLUMN_ROUTINE_BATCH + " TEXT, " //nullable
                + Config.COLUMN_COURSE_CODE + " TEXT, " //nullable
                + Config.COLUMN_COURSE_STATUS + " INTEGER " //nullable
                + ")";
        db.execSQL(CREATE_STUDENT_TABLE);
        Log.e("b","created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_ROUTINE);

        // Create tables again
        onCreate(db);
    }



    public void saveToLocalDatabase(Routine routine,SQLiteDatabase database)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_ROUTINE_ID, routine.getRoutineID());
        contentValues.put(Config.COLUMN_TEACHER_CODE,routine.getTeacherCode());
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

        database.insert(Config.TABLE_ROUTINE,null,contentValues);

        Log.e("akter2","saved");

    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database)
    {

        String[] columns={Config.COLUMN_TEACHER_CODE,Config.COLUMN_COURSE_CODE,Config.COLUMN_ROUTINE_TIME,
        Config.COLUMN_ROUTINE_ROOM,Config.COLUMN_COURSE_CODE,Config.COLUMN_ROUTINE_DAY};


        return (database.query(Config.TABLE_ROUTINE,columns,null,null,null,null,null));


    }

    public  void deleteData(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {

            db.execSQL("DELETE FROM " + Config.TABLE_ROUTINE);

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

    }





































}