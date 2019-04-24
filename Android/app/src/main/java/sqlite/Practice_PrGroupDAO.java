package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Practice;
import model.PracticeGroup;
import model.Practice_PrGroup;

public class Practice_PrGroupDAO extends DBManager {
    private static final String TABLE_NAME = "PRACTICE_PRGROUP";
    private static final String COLUMN_IDPRACTICEGROUP = "IdPracticegroup";
    private static final String COLUMN_IDPRACTICE = "IdPractice";
    private Context context;

    public Practice_PrGroupDAO(Context context) {
        super(context);
        this.context = context;
    }
    public int getGroup_PracticeDatabaseCount() {
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
    public void addPractice_PrGroup(Practice_PrGroup practice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_IDPRACTICEGROUP, practice.getPracticeGroup().getId());
            contentValues.put(COLUMN_IDPRACTICE, practice.getPractice().getId());
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
    }

    public Practice_PrGroup getPractice_GroupById(int idPracticeGroup, int idPractice) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_IDPRACTICEGROUP, COLUMN_IDPRACTICE},
                    COLUMN_IDPRACTICEGROUP + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(idPracticeGroup), String.valueOf(idPractice)},
                    null, null, null
            );
            if (cursor != null) cursor.moveToFirst();
            return new Practice_PrGroup(new PracticeDAO(context).getPracticeById(idPractice), new PracticeGroupDAO(context).getPracticeGroupById(idPracticeGroup));
        } catch (Exception e) {
            Log.e("Err: ", e.getMessage());
        }
        return null;
    }

    public List<Practice_PrGroup> getAllPractice_PrGroup() {
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            List<Practice_PrGroup> listPractice_PrGroup = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    Practice practice = new PracticeDAO(context).getPracticeById(cursor.getInt(1));
                    PracticeGroup practiceGroup = new PracticeGroupDAO(context).getPracticeGroupById(cursor.getInt(0));
                    Practice_PrGroup practice_prGroup = new Practice_PrGroup(practice, practiceGroup);
                    listPractice_PrGroup.add(practice_prGroup);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            return listPractice_PrGroup;
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public List<Practice> getListPracticeByIdGroup(int id) {
        try {
            List<Practice> listPractice = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_IDPRACTICEGROUP, COLUMN_IDPRACTICE},
                    COLUMN_IDPRACTICEGROUP + "=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Practice practice  = new PracticeDAO(context).getPracticeById(cursor.getInt(1));
                    listPractice.add(practice);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
            return listPractice;
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
        return null;
    }
    public void updatePractice_PrGroup(Practice_PrGroup practice_prGroup) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_IDPRACTICEGROUP, practice_prGroup.getPracticeGroup().getId());
            contentValues.put(COLUMN_IDPRACTICE, practice_prGroup.getPractice().getId());
            int update = sqLiteDatabase.update(TABLE_NAME, contentValues,
                    COLUMN_IDPRACTICEGROUP + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(practice_prGroup.getPracticeGroup().getId()),String.valueOf(practice_prGroup.getPractice().getId())});
            Log.d("Updated: ", update + "");
            sqLiteDatabase.close();
        } catch (Exception ex) {
            Log.e("Err:", ex.getMessage());
        }
    }

    public void deleteById(Practice_PrGroup practice_prGroup) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME,
                    COLUMN_IDPRACTICEGROUP + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                    new String[]{String.valueOf(practice_prGroup.getPracticeGroup().getId()),String.valueOf(practice_prGroup.getPractice().getId())});
            db.close();
        } catch (Exception ex) {
            Log.e("Err: ", ex.getMessage());
        }
    }
    public void deleteByIdGroup(int id) {
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            List<Practice> practiceList = this.getListPracticeByIdGroup(id);
            Log.e("SIZE DELETE", practiceList.size()+"");
            for(int i=0; i<practiceList.size();i++) {
                Log.e("ID DELETE", i+"");
                sqLiteDatabase.delete(TABLE_NAME,
                        COLUMN_IDPRACTICEGROUP + "=?"+" AND "+ COLUMN_IDPRACTICE + "=?",
                        new String[]{String.valueOf(id),String.valueOf(practiceList.get(i).getId())});
                sqLiteDatabase.close();
            }
            sqLiteDatabase.close();
        }catch (Exception ex){

        }
    }
}
