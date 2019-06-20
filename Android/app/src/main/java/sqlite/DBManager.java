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
import model.Practice;

public class DBManager extends SQLiteOpenHelper {
    private Context context;
    private static final String TAG = "SQLite";
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
            "Description VARCHAR(200)," +
            "Locked INTEGER" +
            ")";
    private static final String CREATE_PRACTICE_TABLE = "CREATE TABLE IF NOT EXISTS PRACTICE(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Avatar INTEGER," +
            "Description VARCHAR(200)" +
            ")";
    private static final String CREATE_GUIDE_TABLE = "CREATE TABLE IF NOT EXISTS GUIDE(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Image INTEGER," +
            "Description VARCHAR(200)" +
            ")";

    private static final String CREATE_PRACTICE_PRGROUP = "CREATE TABLE IF NOT EXISTS PRACTICE_PRGROUP(" +
            "IdPracticegroup INTEGER," +
            "IdPractice INTEGER," +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CONSTRAINT fk_idpracticegroup_" +
            "   FOREIGN KEY (IdPracticegroup)" +
            "   REFERENCES PRACTICEGROUP(Id)," +
            "CONSTRAINT fk_fk_idpractice_" +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)" +
            ")";
    private static final String CREATE_GUIDE_PRACTICE = "CREATE TABLE IF NOT EXISTS GUIDE_PRACTICE(" +
            "IdGuide INTEGER," +
            "IdPractice INTEGER," +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CONSTRAINT fk_idpractice_" +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)," +
            "CONSTRAINT fk_guide_" +
            "   FOREIGN KEY (IdGuide)" +
            "   REFERENCES GUIDE(Id)" +
            ")";
    private static final String CREATE_CUSTOMWORK_PRACTICE = "CREATE TABLE IF NOT EXISTS CUSTOMWORK_PRACTICE(" +
            "IdPractice INTEGER," +
            "IdCustomWork INTEGER," +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "CONSTRAINT fk_idpractice_" +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)," +
            "CONSTRAINT fk_custom_" +
            "   FOREIGN KEY (IdCustomWork)" +
            "   REFERENCES CUSTOM(Id)" +
            ")";
    private static final String CREATE_CUSTOM_TABLE = "CREATE TABLE IF NOT EXISTS CUSTOM(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Avatar INTEGER," +
            "Description VARCHAR(200)" +
            ")";

    private static final String CREATE_LOCKED = "CREATE TABLE IF NOT EXISTS LOCKED(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "PRICE DOUBLE," +
            "IdGroup INTEGER," +
            "CONSTRAINT fk_locked_" +
            "   FOREIGN KEY (IdGroup)" +
            "   REFERENCES PRACTICEGROUP(Id)" +
            ")";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "GuideDatabase.onCreate");
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_TABLE);
        sqLiteDatabase.execSQL(CREATE_GUIDE_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_PRGROUP);
        sqLiteDatabase.execSQL(CREATE_GUIDE_PRACTICE);
        sqLiteDatabase.execSQL(CREATE_CUSTOM_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMWORK_PRACTICE);
        sqLiteDatabase.execSQL(CREATE_LOCKED);
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
        sqLiteDatabase.execSQL(CREATE_CUSTOM_TABLE);
        sqLiteDatabase.execSQL(CREATE_CUSTOMWORK_PRACTICE);
        onCreate(sqLiteDatabase);
    }

}
