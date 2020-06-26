package com.kulodev.smkppi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SMKPPI";

    // Table
    private static final String TABLE_NOTIFICATION = "tbl_notification";


    // Coloumn Table Notification
    public static final String ID_NOTIFICATION = "id_notification";
    public static final String TITLE_NOTIFICATION = "title_notification";
    public static final String ISI_NOTIFICATION = "isi_notification";
    public static final String LINK_NOTIFICATION= "link_notification";
    public static final String STATUS_DIBACA= "status_dibaca";


    // Table Create Statements
    // Todo table create statement
    private static final String NOTIFICATION = "CREATE TABLE "
            + TABLE_NOTIFICATION + "(" + ID_NOTIFICATION + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_NOTIFICATION
            + " VARCHAR, " +  ISI_NOTIFICATION + " VARCHAR, " + LINK_NOTIFICATION + " VARCHAR, "+ STATUS_DIBACA + " TINYINT);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTIFICATION);
    }

    //upgrading the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String notification = "DROP TABLE IF EXISTS "+TABLE_NOTIFICATION;
        db.execSQL(notification);
        onCreate(db);
    }

    public Cursor selectNotification() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NOTIFICATION;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public boolean addNotification(int id_notification, String title_notification,
                                   String isi_notification , String link_notification,
                                   int status_dibaca) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_NOTIFICATION, id_notification);
        contentValues.put(TITLE_NOTIFICATION, title_notification);
        contentValues.put(ISI_NOTIFICATION, isi_notification);
        contentValues.put(LINK_NOTIFICATION, link_notification);
        contentValues.put(STATUS_DIBACA, status_dibaca);


        db.insert(TABLE_NOTIFICATION, null, contentValues);
        db.close();
        return true;
    }


    public boolean updateStatusNotification(int id_notification, int status_dibaca) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS_DIBACA, status_dibaca);
        db.update(TABLE_NOTIFICATION, contentValues, ID_NOTIFICATION + "=" + id_notification, null);
        db.close();
        return true;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NOTIFICATION);
        db.close();
    }


}
