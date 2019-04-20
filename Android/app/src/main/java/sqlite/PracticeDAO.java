package sqlite;

import android.content.Context;

public class PracticeDAO extends DBManager{
    private static final String TABLE_NAME = "PRACTICE";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_DESCIPTION = "Description";

    public PracticeDAO(Context context)  {
        super(context);
    }

}
