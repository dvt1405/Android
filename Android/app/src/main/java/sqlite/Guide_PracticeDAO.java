package sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.Guide_Practice;
import model.Practice;

public class Guide_PracticeDAO extends DBManager {
    private static final String TABLE_NAME = "GUIDE_PRACTICE";
    private static final String COLUMN_IDGUIDE = "IdGuide";
    private static final String COLUMN_IDPRACTICE = "IdPractice";
    private Context context;

    public Guide_PracticeDAO(Context context) {
        super(context);
        this.context = context;
    }
    public int getGuide_PracticeDatabaseCount() {
        try {
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            int count = cursor.getCount();
            cursor.close();
            db.close();
            return count;
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return 0;
    }
    public void addGuide_Practice(Guide_Practice guide_practice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_IDGUIDE, guide_practice.getGuide().getId());
            contentValues.put(COLUMN_IDPRACTICE, guide_practice.getPractice().getId());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
    }

    public Guide_Practice getGuide_PracticeById(int idGuide, int idPractice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            return new Guide_Practice(new PracticeDAO(context).getPracticeById(idPractice), new GuideDAO(context).getGuideDatabaseById(idGuide));
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
        return null;
    }

    public List<Guide_Practice> getAllGuide_Practice() {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            List<Guide_Practice> listPractice_Guide = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    Practice practice = new PracticeDAO(context).getPracticeById(cursor.getInt(1));
                    Guide guide = new GuideDAO(context).getGuideDatabaseById(cursor.getInt(0));
                    Guide_Practice practice_guide = new Guide_Practice(practice, guide);
                    listPractice_Guide.add(practice_guide);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            return listPractice_Guide;
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public List<Guide> getListGuideByIdPractice(int id) {
        try {
            List<Guide> listGuide = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_IDPRACTICE,COLUMN_IDGUIDE},
                    COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Guide guide = new GuideDAO(context).getGuideDatabaseById(cursor.getInt(1));
                    listGuide.add(guide);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            return listGuide;
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public void updateGuide_Practice(Guide_Practice guide_practice) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_IDGUIDE, guide_practice.getGuide().getId());
            contentValues.put(COLUMN_IDPRACTICE, guide_practice.getPractice().getId());
            int update = sqLiteDatabase.update(TABLE_NAME, contentValues,
                    COLUMN_IDGUIDE + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(guide_practice.getGuide().getId()),String.valueOf(guide_practice.getPractice().getId())});
            sqLiteDatabase.close();
            Log.d("Updated: ", update + "");
        } catch (Exception ex) {
            Log.e("Err:", ex.getMessage());
        }
    }

    public void deleteById(Guide_Practice guide_practice) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME,
                    COLUMN_IDGUIDE + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(guide_practice.getGuide().getId()),String.valueOf(guide_practice.getPractice().getId())});
            db.close();
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
    }
}
