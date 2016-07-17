package DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carsten on 17.07.2016.
 */



public class LvlDataSource {

    private static final String LOG_TAG = "DATABASE";

    private SQLiteDatabase database;
    private LvlDbHelper dbHelper;

    private String[] columns = {
            LvlDbHelper.COLUMN_ID,
            LvlDbHelper.COLUMN_LEVEL,
            LvlDbHelper.COLUMN_ENEMYTYPE,
            LvlDbHelper.COLUMN_TIME,
            LvlDbHelper.COLUMN_POSX,
            LvlDbHelper.COLUMN_POSY,
            LvlDbHelper.COLUMN_MOVEMENTTYPE
    };

    public LvlDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new LvlDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }
}
