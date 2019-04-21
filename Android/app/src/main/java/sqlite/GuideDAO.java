package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.Practice;

public class GuideDAO extends DBManager {
    private static final String TABLE_NAME = "Guide";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_DESCIPTION = "Description";
    private Context context;
    public GuideDAO(Context context) {
        super(context);
        this.context = context;
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
        try{
            String query = "SELECT * FROM "+TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }catch (Exception ex){
            Log.e("Err: ", ex.getMessage());
        }
        return 0;
    }
    public void addGuideDatabase(Guide guide) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, guide.getName());
            values.put(COLUMN_IMAGE, guide.getImage());
            values.put(COLUMN_DESCIPTION, guide.getDescription());
            db.insert(TABLE_NAME,null,values);
            db.close();
        }catch (Exception ex){
            Log.e("Err: ", ex.getMessage());
        }
    }
    public Guide getGuideDatabaseById(int id) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_DESCIPTION},
                    COLUMN_ID+"=?", new String[]{String.valueOf(id)},null,null,null,null);

            if(cursor!=null) cursor.moveToFirst();
            Guide guide = new Guide(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)), cursor.getString(3));
            return guide;
        }catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public List<Guide> getAllGuideDataBase() {
        try{
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
        }catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public void deleteById(Guide guide){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME,COLUMN_ID+"=?", new String[]{String.valueOf(guide.getId())});
            db.close();
        }catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
    }

}
