package cn.hnust.jsj.smartdiary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_TIME = "time";
    static final String KEY_TITLE = "title";
    static final String KEY_CONTENT = "content";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDiaryDB";
    static final String DATABASE_TABLE = "diary";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table diary (_id integer primary key autoincrement, "
        + "time text not null, title text not null, content text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS diary");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //---insert a diary into the database---
    public long insertDiary(String title, String content) 
    {
        ContentValues initialValues = new ContentValues();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dates =  Calendar.getInstance().getTime();
        String time= date_format.format(dates);  
        initialValues.put(KEY_TIME, time);
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_CONTENT, content);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular diary---
    public boolean deleteDiary(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the diary---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TIME, KEY_TITLE,
                KEY_CONTENT}, null, null, null, null, null);
    }

    //---retrieves a particular diary---
    public Cursor getDiary(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                		KEY_TIME, KEY_TITLE, KEY_CONTENT}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a diary---
    public boolean updateDiary(long rowId, String title, String content) 
    {
        ContentValues args = new ContentValues();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dates =  Calendar.getInstance().getTime();
        String time= date_format.format(dates);  
        args.put(KEY_TIME, time);
        args.put(KEY_TITLE, title);
        args.put(KEY_CONTENT, content);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
