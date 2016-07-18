package DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
//import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Carsten on 17.07.2016.
 */
public class LvlDbHelper extends SQLiteAssetHelper{

    public static final String DB_NAME = "Leveldesign.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_LEVELDESIGN = "Levels";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_LEVEL = "LevelNr";
    public static final String COLUMN_ENEMYTYPE = "EnemyType";
    public static final String COLUMN_TIME = "AppearanceTime";
    public static final String COLUMN_POSX = "PositionX";
    public static final String COLUMN_POSY = "PositionY";
    public static final String COLUMN_MOVEMENTTYPE = "MovementType";

    /**public static final String SQL_CREATE =
            "(" + COLUMN_ID	+ "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            COLUMN_LEVEL + "INTEGER NOT NULL DEFAULT 1, " +
            COLUMN_ENEMYTYPE + "TEXT NOT NULL DEFAULT 'TOWER', " +
            COLUMN_TIME	+ "REAL NOT NULL DEFAULT 0, " +
            COLUMN_POSX + "INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_POSY + "INTEGER NOT NULL DEFAULT 0, " +
            COLUMN_MOVEMENTTYPE	+ "TEXT NOT NULL DEFAULT 'STRAIGHT')";*/

    private static final String LOG_TAG = "DATABASE";


   /** public LvlDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }*/
    /**public LvlDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }*/
    public LvlDbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    //    mContext = ctx;
        setForcedUpgrade();
    }
    //@Override
    //public void onCreate(SQLiteDatabase db) {    }

    //@Override
    //public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }
}
