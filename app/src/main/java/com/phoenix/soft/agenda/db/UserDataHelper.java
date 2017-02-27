package com.phoenix.soft.agenda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry.INCOME;
import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry.NAME;
import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry.OUTCOME;
import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry.PIC_URL;
import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry.TABLE_NAME;
import static com.phoenix.soft.agenda.db.UserAccountContract.AccountEntry._ID;

/**
 * Created by yaoda on 27/02/17.
 */

public class UserDataHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME + " TEXT," +
                    OUTCOME + " TEXT," +
                    PIC_URL + " TEXT," +
                    INCOME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public UserDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
