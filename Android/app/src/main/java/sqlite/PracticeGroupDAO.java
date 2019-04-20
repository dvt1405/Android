package sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Guide;
import model.PracticeGroup;

public class PracticeGroupDAO extends DBManager{
    private static final String TABLE_NAME = "PRACTICE";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_AVATAR = "Avatar";
    private static final String COLUMN_DESCIPTION = "Description";

    public PracticeGroupDAO(Context context) {
        super(context);
    }

}
