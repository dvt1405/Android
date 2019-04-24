package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import com.fitness.R;

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
    public void createDefaultDataPractice(){
        if(this.getPracticeDatabaseCount()==0){
            Practice practice1 = new Practice("Cơ tay trước, sau", R.drawable.co_tay_truoc,"Bài tập cơ tay 1");
            Practice practice2 = new Practice("Chống đẩy", R.drawable.chongday,"Bài tập cơ tay 2");
            Practice practice3 = new Practice("Gập người nâng tạ", R.drawable.gap_nguoi_nangta,"Bài tập cơ tay 3");
            Practice practice4 = new Practice("Cơ đùi cơ bản", R.drawable.nang_chan,"Bài tập cơ tay 4");
            Practice practice5 = new Practice("Cơ vai, ngực ", R.drawable.plank,"Bài tập cơ tay 5");
            this.addPractice(practice1);
            this.addPractice(practice2);
            this.addPractice(practice3);
            this.addPractice(practice4);
            this.addPractice(practice5);
        }
    }

    public int getPracticeDatabaseCount() {
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
            Practice practice = new Practice(cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            practice.setId(cursor.getInt(0));
            cursor.close();
            sqLiteDatabase.close();
            return practice;
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
            cursor.close();
            sqLiteDatabase.close();
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
            sqLiteDatabase.close();
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
