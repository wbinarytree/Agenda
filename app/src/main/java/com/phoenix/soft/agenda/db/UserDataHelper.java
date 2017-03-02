package com.phoenix.soft.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phoenix.soft.agenda.module.Account;

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
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Account.db";

    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME + " TEXT," +
                    OUTCOME + " TEXT," +
                    PIC_URL + " TEXT," +
                    INCOME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static UserDataHelper sInstance;

    public UserDataHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);

        // create new table
        onCreate(db);
    }

    public void addAccount(Account account){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME,account.getAccountName());
        cv.put(INCOME,account.getIncome().toString());
        cv.put(OUTCOME,account.getOutcome().toString());
        database.insert(TABLE_NAME, null, cv );
    }

    public static synchronized UserDataHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new UserDataHelper(context.getApplicationContext());
        }
        return sInstance;
    }

}
