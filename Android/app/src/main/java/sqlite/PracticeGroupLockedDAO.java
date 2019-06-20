package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fitness.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import model.Guide;
import model.Practice;
import model.PracticeGroup;

public class PracticeGroupLockedDAO extends DBManager {
    private static final String TABLE_NAME = "PRACTICEGROUP";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_AVATAR = "Avatar";
    private static final String COLUMN_DESCIPTION = "Description";
    private static final String COLUMN_LOCK = "Locked";
    private Context context;
    public PracticeGroupLockedDAO(Context context) {
        super(context);
        this.context = context;
    }
    public void createDefaultGroupData(){
        if(this.getPracticeGroupDatabaseCount()==0) {
            PracticeGroup pg1 = new PracticeGroup("Cơ tay", R.drawable.co_tay_truoc,"Bài tập cơ tay");
            PracticeGroup pg2 = new PracticeGroup("Cơ vai", R.drawable.plank,"Bài tập cơ tay");
            PracticeGroup pg3 = new PracticeGroup("Cơ ngực", R.drawable.chongday,"Bài tập cơ tay");
            PracticeGroup pg4 = new PracticeGroup("Cơ chân", R.drawable.nang_chan,"Bài tập cơ tay");
            PracticeGroup pg5 = new PracticeGroup("Tổng hợp 1", R.drawable.jumpsquat,"Bài tập cơ tay");
            pg1.setLocked(0);
            pg2.setLocked(0);
            pg3.setLocked(0);
            pg4.setLocked(0);
            pg5.setLocked(1);
            this.addPracticeGroup(pg1);
            this.addPracticeGroup(pg2);
            this.addPracticeGroup(pg3);
            this.addPracticeGroup(pg4);
            this.addPracticeGroup(pg5);
        }
    }
    public int getPracticeGroupDatabaseCount() {
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
    public void addPracticeGroup(PracticeGroup practice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, practice.getName());
            contentValues.put(COLUMN_AVATAR, practice.getAvatar());
            contentValues.put(COLUMN_DESCIPTION, practice.getDescription());
            contentValues.put(COLUMN_LOCK, practice.getLocked());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            Log.e("Run to here: ", "add done");
            sqLiteDatabase.close();
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
            cursor.close();
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
            cursor.close();
            sqLiteDatabase.close();
            return practiceGroup;
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return null;
    }

    public List<PracticeGroup> getAllPracticeGroup() {
        String sql = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_DESCIPTION+" != 'custom' AND "+COLUMN_LOCK+"=0";
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
                    cursor.close();
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
    public List<PracticeGroup> getAllPracticeGroupLocked() {
        String sql = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_DESCIPTION+" != 'custom' AND "+COLUMN_LOCK+"=1";
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
                    cursor.close();
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
    public List<PracticeGroup> getAllCustom() {
        String sql = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_DESCIPTION+" = 'custom'";
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
                    cursor.close();
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
