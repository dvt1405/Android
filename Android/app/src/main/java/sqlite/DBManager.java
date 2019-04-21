package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Guide;

public class DBManager extends SQLiteOpenHelper {
    private static final String TAG ="SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FITNESS_DATA";
//---------GUIDE--------------------
    private static final String TABLE_NAME = "Guide";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_DESCIPTION = "Description";
//--------Create Table--------------
    private static final String CREATE_PRACTICEGROUP_TABLE = "CREATE TABLE IF NOT EXISTS PRACTICEGROUP(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Avatar INTEGER," +
            "Description VARCHAR(200)" +
            ")";
    private static final String CREATE_PRACTICE_TABLE = "CREATE TABLE IF NOT EXISTS PRACTICE(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Avatar INTEGER," +
            "Description VARCHAR(200)," +
            "IdPracticegroup INTEGER" +
            ")";
    private static  final String CREATE_GUIDE_TABLE ="CREATE TABLE IF NOT EXISTS GUIDE(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Image INTEGER," +
            "IdPractice INTEGER," +
            "Description VARCHAR(200)" +
            ")";

    private static final String CREATE_PRACTICE_PRGROUP = "CREATE TABLE IF NOT EXISTS PRACTICE_PRGROUP(" +
            "IdPracticegroup INTEGER PRIMARY KEY," +
            "IdPractice INTEGER PRIMARY KEY," +
            "CONSTRAINT fk_idpracticegroup_" +
            "   FOREIGN KEY (IdPracticegroup)" +
            "   REFERENCES PRACTICEGROUP(Id)," +
            "CONSTRAINT fk_fk_idpractice_" +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)" +
            ")";
    private static final String CREATE_GUIDE_PRACTICE = "CREATE TABLE IF NOT EXISTS GUIDE_PRACTICE(" +
            "IdGuide INTEGER PRIMARY KEY," +
            "IdPractice INTEGER PRIMARY KEY," +
            "CONSTRAINT fk_idpractice_" +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)," +
            "CONSTRAINT fk_guide_" +
            "   FOREIGN KEY (IdGuide)" +
            "   REFERENCES GUIDE(Id)" +
            ")";
    public DBManager(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "GuideDatabase.onCreate");
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_TABLE);
        sqLiteDatabase.execSQL(CREATE_GUIDE_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_PRGROUP);
        sqLiteDatabase.execSQL(CREATE_GUIDE_PRACTICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "GuidDatabse.onUpgrate...");
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_TABLE);
        sqLiteDatabase.execSQL(CREATE_GUIDE_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_PRGROUP);
        sqLiteDatabase.execSQL(CREATE_GUIDE_PRACTICE);
        onCreate(sqLiteDatabase);
    }

}
