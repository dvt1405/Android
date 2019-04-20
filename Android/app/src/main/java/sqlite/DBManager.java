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
            "IdPracticegroup INTEGER," +
            "CONSTRAINT fk_idpracticegroup " +
            "   FOREIGN KEY (IdPracticegroup)" +
            "   REFERENCES PRACTICEGROUP(Id)" +
            ")";
    private static  final String CREATE_GUIDE_TABLE ="CREATE TABLE IF NOT EXISTS GUIDE(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name VARCHAR(200)," +
            "Image INTEGER," +
            "IdPractice INTEGER," +
            "Description VARCHAR(200)," +
            "CONSTRAINT fk_idpractice " +
            "   FOREIGN KEY (IdPractice)" +
            "   REFERENCES PRACTICE(Id)" +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "GuidDatabse.onUpgrate...");
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICEGROUP_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRACTICE_TABLE);
        sqLiteDatabase.execSQL(CREATE_GUIDE_TABLE);
        onCreate(sqLiteDatabase);
    }
    public void onCreateDefaultGuideDatabase(){
        if(this.getGuideDatabaseCount()==0) {
            Guide guide1 = new Guide("Default 1", 1, "Default data 1");
            Guide guide2 = new Guide("Default 2", 1, "Default data 2");
            this.addGuideDatabase(guide1);
            this.addGuideDatabase(guide2);
        }
    }
    public int getGuideDatabaseCount() {
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public void addGuideDatabase(Guide guide) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, guide.getName());
        values.put(COLUMN_IMAGE, guide.getImage());
        values.put(COLUMN_DESCIPTION, guide.getDescription());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public Guide getGuideDatabaseById(int id) {
        Log.i(TAG, "MyDatabaseHelper.getGuide... " + id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_DESCIPTION},
               COLUMN_ID+"=?", new String[]{String.valueOf(id)},null,null,null,null);

        if(cursor!=null) cursor.moveToFirst();
        Guide guide = new Guide(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        return guide;
    }
    public List<Guide> getAllGuideDataBase() {
        List<Guide> listGuide = new ArrayList<>();
        String sqlite = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlite,null);
        if(cursor.moveToFirst()){
            do{
                Guide guide = new Guide();
                guide.setId(cursor.getInt(0));
                guide.setName(cursor.getString(1));
                guide.setImage(cursor.getInt(2));
                guide.setDescription(cursor.getString(3));

                listGuide.add(guide);
            }while(cursor.moveToNext());
        }
        return listGuide;
    }
    public void deleteById(Guide guide){
       SQLiteDatabase db = this.getWritableDatabase();
       db.delete(TABLE_NAME,COLUMN_ID+"=?", new String[]{String.valueOf(guide.getId())});
       db.close();
    }
}
