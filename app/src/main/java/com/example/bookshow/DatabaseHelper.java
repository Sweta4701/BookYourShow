package com.example.bookshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    public static final String DBName = "login.db";
    public static final int DBVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, DBName, null, DBVersion);
        Log.i(TAG, "DBHelper: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate: ");
        sqLiteDatabase.execSQL("create table users(username  TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public boolean checkUsername(String username) {
        Log.i(TAG, "checkUsername: username: " + username);
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }

    public boolean checkUser(String username, String pwd) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, pwd});
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }
}
