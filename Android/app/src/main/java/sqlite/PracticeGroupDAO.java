package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Guide;
import model.Practice;
import model.PracticeGroup;

public class PracticeGroupDAO extends DBManager {
    private static final String TABLE_NAME = "PRACTICEGROUP";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_AVATAR = "Avatar";
    private static final String COLUMN_DESCIPTION = "Description";
    private Context context;
    public PracticeGroupDAO(Context context) {
        super(context);
        this.context = context;
    }

    public void addPracticeGroup(PracticeGroup practice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, practice.getName());
            contentValues.put(COLUMN_AVATAR, practice.getAvatar());
            contentValues.put(COLUMN_DESCIPTION, practice.getDescription());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            Log.e("Rung to here: ", "add done");
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
    }

    public PracticeGroup getPracticeGroupById(int id) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_AVATAR, COLUMN_DESCIPTION},
                    COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null
            );
            if (cursor != null) cursor.moveToFirst();
            PracticeGroup practiceGroup = new PracticeGroup(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            sqLiteDatabase.close();
            return practiceGroup;
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return null;
    }
    public PracticeGroup getPracticeGroupByName(String name) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_AVATAR, COLUMN_DESCIPTION},
                    COLUMN_NAME + "=?", new String[]{String.valueOf(name)}, null, null, null
            );
            if (cursor != null) cursor.moveToFirst();
            PracticeGroup practiceGroup = new PracticeGroup(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            sqLiteDatabase.close();
            return practiceGroup;
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return null;
    }

    public List<PracticeGroup> getAllPracticeGroup() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        try {
            List<PracticeGroup> listPracticeGroup = new ArrayList<>();
            try {
                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
                if (cursor.moveToFirst()) {
                    do {
                        PracticeGroup practiceGroup = new PracticeGroup();
                        practiceGroup.setId(cursor.getInt(0));
                        practiceGroup.setName(cursor.getString(1));
                        practiceGroup.setAvatar(cursor.getInt(2));
                        practiceGroup.setDescription(cursor.getString(3));
                        listPracticeGroup.add(practiceGroup);
                    } while (cursor.moveToNext());
                    sqLiteDatabase.close();
                }
            } catch (Exception ex) {

            }
            return listPracticeGroup;
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
        return null;
    }

    public void updatePractice(PracticeGroup practiceGroup) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, practiceGroup.getName());
            contentValues.put(COLUMN_AVATAR, practiceGroup.getAvatar());
            contentValues.put(COLUMN_DESCIPTION, practiceGroup.getDescription());
            int update = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(practiceGroup.getId())});
            Log.d("Updated: ", update + "");
            sqLiteDatabase.close();
        } catch (Exception ex) {
            Log.e("Err:", ex.getMessage());
        }
    }

    public void deleteById(PracticeGroup practiceGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(practiceGroup.getId())});
        db.close();
    }

}
