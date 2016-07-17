package DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Carsten on 17.07.2016.
 */



public class LvlDataSource {

    private static final String LOG_TAG = LvlDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private LvlDbHelper dbHelper;


    public LvlDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new LvlDbHelper(context);
    }
}
