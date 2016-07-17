package DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Carsten on 17.07.2016.
 */
public class LvlDbHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "Leveldesign.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_LEVELDESIGN = "Leveldesign";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_ENEMYTYPE = "EnemyType";
    public static final String COLUMN_TIME = "Time";
    public static final String COLUMN_POSX = "PositionX";
    public static final String COLUMN_POSY = "PositionY";
    public static final String COLUMN_MOVEMENTTYPE = "MovementType";

    public static final String SQL_CREATE =
            "(" + COLUMN_ID	+ "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            COLUMN_LEVEL + "ITEGER NOT NULL DEFAULT 1, " +
            COLUMN_ENEMYTYPE + "TEXT NOT NULL DEFAULT 'STANDARD', " +
            COLUMN_TIME	+ "REAL NOT NULL DEFAULT 0, " +
            COLUMN_POSX + "INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_POSY + "INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_MOVEMENTTYPE	+ "TEXT NOT NULL DEFAULT 'Straight')";

    private static final String LOG_TAG = LvlDbHelper.class.getSimpleName();


    public LvlDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
