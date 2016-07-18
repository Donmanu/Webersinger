package DatabaseAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;

import com.badlogic.gdx.utils.Array;
import com.don.galaxydefender.android.logic.Enemy;
import com.don.galaxydefender.android.logic.EnemyType;
import com.don.galaxydefender.android.logic.MovementType;

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

    /**
        Methode um Enemy in Leveldesign
     */
    public Enemy insertEnemy(int lvlNr, String enemyType, double time, int posX, int posY, String MovType) {
        ContentValues values = new ContentValues();
        values.put(LvlDbHelper.COLUMN_LEVEL, lvlNr);
        values.put(LvlDbHelper.COLUMN_ENEMYTYPE, enemyType);
        values.put(LvlDbHelper.COLUMN_TIME, time);
        values.put(LvlDbHelper.COLUMN_POSX, posX);
        values.put(LvlDbHelper.COLUMN_POSY, posY);
        values.put(LvlDbHelper.COLUMN_MOVEMENTTYPE, MovType);

        long insertId = database.insert(LvlDbHelper.TABLE_LEVELDESIGN, null, values);

        Cursor cursor = database.query(LvlDbHelper.TABLE_LEVELDESIGN,
                columns,LvlDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Enemy enemy = cursorToEnemy(cursor);
        cursor.close();

        return enemy;
    }

    private Enemy cursorToEnemy(Cursor cursor) {
        //int idIndex = cursor.getColumnIndex(LvlDbHelper.COLUMN_ID);
        //int idLevel = cursor.getColumnIndex(LvlDbHelper.COLUMN_LEVEL);
        int idEnemyType = cursor.getColumnIndex(LvlDbHelper.COLUMN_ENEMYTYPE);
        int idTime = cursor.getColumnIndex(LvlDbHelper.COLUMN_TIME);
        int idPosX = cursor.getColumnIndex(LvlDbHelper.COLUMN_POSX);
        int idPosY = cursor.getColumnIndex(LvlDbHelper.COLUMN_POSY);
        int idMovType = cursor.getColumnIndex(LvlDbHelper.COLUMN_MOVEMENTTYPE);

        //int lvl = cursor.getInt(idLevel);
        EnemyType enemyType = EnemyType.valueOf(cursor.getString(idEnemyType));
        double time = cursor.getDouble(idTime);
        int posX = cursor.getInt(idPosX);
        int posY = cursor.getInt(idPosY);
        MovementType movType = MovementType.valueOf(cursor.getString(idMovType));

        Enemy enemy = new Enemy(enemyType, posX, posY, movType, time);

        return enemy;
    }

    public Array<Enemy> getAllEnemies() {
        Array<Enemy> enemyList = new Array<>();

        Cursor cursor = database.query(LvlDbHelper.TABLE_LEVELDESIGN,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Enemy enemy;

        while(!cursor.isAfterLast()) {
            enemy = cursorToEnemy(cursor);
            enemyList.add(enemy);
            Log.d(LOG_TAG, "EnemyType: " + enemy.getEnemyType() + ", AppearanceTime: " + enemy.getAppearanceTime());
            cursor.moveToNext();
        }

        cursor.close();

        return enemyList;
    }
}
