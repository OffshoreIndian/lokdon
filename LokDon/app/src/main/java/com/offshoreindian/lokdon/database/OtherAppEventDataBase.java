package com.offshoreindian.lokdon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.offshoreindian.lokdon.utils.DebugUtil;

/**
 * Created by praveshkumar on 13/10/16.
 */

public class OtherAppEventDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_event_db";
    private static final int DATABASE_VERSION = 2;
    private static OtherAppEventDataBase instance = null;

    private String TABLE_NAME = "app_detail";
    private String COLUMN_ID = "id";
    private String COLUMN_APP_NAME = "aap_name";
    private String COLUMN_APP_PACKAGE = "package_name";
    private String COLUMN_CAN_ACCESS = "can_access";
    private String COLUMN_DONT_ASK = "dont_ask";


    private String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID
            + " integer , "
            + COLUMN_APP_NAME + " text ," +
            COLUMN_APP_PACKAGE + " text primary key ," +
            COLUMN_CAN_ACCESS + " text," +
            COLUMN_DONT_ASK + " text" +
            ");";


    public static OtherAppEventDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new OtherAppEventDataBase(context);
        }
        return instance;
    }

    private OtherAppEventDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public void insertAppInfo(String appName, String packageName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_APP_NAME, appName);
            values.put(COLUMN_APP_PACKAGE, packageName);
            values.put(COLUMN_DONT_ASK, "N");
            values.put(COLUMN_CAN_ACCESS, "N");

            // Inserting Row
            db.insert(TABLE_NAME, null, values);
            db.close(); // Clo

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean ifExists(String packageName) {

        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_APP_PACKAGE + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[]{packageName});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean isDontAskSelected(String packageName)
    {
        boolean dontAsk = false;
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_APP_PACKAGE + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[]{packageName});
        if(cursor !=null)
        {
            cursor.moveToNext();
            if(cursor.getString(4).equalsIgnoreCase("Y"))
                dontAsk = true;
            else
                dontAsk = false;
        }
         cursor.close();
        return dontAsk;
    }

    public boolean isDefaultSelected(String packageName)
    {
        boolean dontAsk = false;
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_APP_PACKAGE + " =?";
        Cursor cursor = db.rawQuery(selectString, new String[]{packageName});
        if(cursor !=null)
        {
            cursor.moveToNext();
            if(cursor.getString(3).equalsIgnoreCase("Y"))
                dontAsk = true;
            else
                dontAsk = false;
        }
        cursor.close();
        return dontAsk;
    }
    public void insertDoAskStatus(String packageName,String status)
    {
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "UPDATE "+TABLE_NAME+" SET "+COLUMN_DONT_ASK+" = '"+status+"' WHERE "+COLUMN_APP_PACKAGE+" =?";
        db.execSQL(strSQL,new String[]{packageName});
    }

    public void insertDefaultStatus(String packageName,String status)
    {
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "UPDATE "+TABLE_NAME+" SET "+COLUMN_CAN_ACCESS+" = '"+status+"' WHERE "+COLUMN_APP_PACKAGE+" =?";
        db.execSQL(strSQL,new String[]{packageName});
    }
    public void readDB() {
//        SQLiteDatabase db = this.getReadableDatabase();

//        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
//                        COLUMN_APP_NAME, COLUMN_APP_PACKAGE }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                DebugUtil.printLog("DB READ DATA  ::: " + cursor.getString(2) + "----::");

            } while (cursor.moveToNext());
        }

    }

}
