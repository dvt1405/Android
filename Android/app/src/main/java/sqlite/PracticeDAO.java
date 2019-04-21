package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Practice;

public class PracticeDAO extends DBManager {
    private static final String TABLE_NAME = "PRACTICE";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_AVATAR = "Avatar";
    private static final String COLUMN_DESCIPTION = "Description";
    private Context context;
    public PracticeDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void addPractice(Practice practice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, practice.getName());
            contentValues.put(COLUMN_AVATAR, practice.getAvatar());
            contentValues.put(COLUMN_DESCIPTION, practice.getDescription());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
    }

    public Practice getPracticeById(int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_AVATAR, COLUMN_DESCIPTION},
                    COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor != null) cursor.moveToFirst();
            return new Practice(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
        return null;
    }

    public List<Practice> getAllPractice() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        List<Practice> listPractice = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    Practice practice = new Practice();
                    practice.setId(cursor.getInt(0));
                    practice.setName(cursor.getString(1));
                    practice.setAvatar(cursor.getInt(2));
                    practice.setDescription(cursor.getString(3));
                    listPractice.add(practice);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return listPractice;
    }

    public void updatePractice(Practice practice) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, practice.getName());
            contentValues.put(COLUMN_AVATAR, practice.getAvatar());
            contentValues.put(COLUMN_DESCIPTION, practice.getDescription());
            int update = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(practice.getId())});
            Log.d("Updated: ", update + "");
        } catch (Exception ex) {
            Log.e("Err:", ex.getMessage());
        }
    }

    public void deleteById(Practice practice) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(practice.getId())});
            db.close();
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
    }


}
