/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.phoenix.soft.costy.models.Account;

import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_INCOME;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_NAME;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_OUTCOME;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_PIC_URL;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_TABLE_NAME;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.SQL_CREATE_ACCOUNT_TABLE;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry.SQL_DELETE_ACCOUNT_TABLE;
import static com.phoenix.soft.costy.db.DBContract.AccountEntry._ID;
import static com.phoenix.soft.costy.db.DBContract.DetailEntry.SQL_CREATE_DETAIL_TABLE;
import static com.phoenix.soft.costy.db.DBContract.DetailEntry.SQL_DELETE_DETAIL_TABLE;

/**
 * Created by yaoda on 27/02/17.
 */

public class UserDataHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Account.db";



    private static final String TAG = "database";
    private static UserDataHelper sInstance;



    public UserDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);
        db.execSQL(SQL_CREATE_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ACCOUNT_TABLE);
        db.execSQL(SQL_DELETE_DETAIL_TABLE);

        // create new table
        onCreate(db);
    }

    public void addAccount(Account account) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ACCOUNT_NAME, account.getAccountName());
        cv.put(ACCOUNT_INCOME, account.getIncome().toString());
        cv.put(ACCOUNT_OUTCOME, account.getOutcome().toString());
        database.insert(ACCOUNT_TABLE_NAME, null, cv);
    }

    public long AddOrUpdateAccount(Account account) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ACCOUNT_NAME, account.getAccountName());
            values.put(ACCOUNT_PIC_URL, account.getAccountPicUrl());
            values.put(ACCOUNT_INCOME, account.getIncome().toString());
            values.put(ACCOUNT_OUTCOME, account.getOutcome().toString());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(ACCOUNT_TABLE_NAME, values, ACCOUNT_NAME + "= ?", new String[]{account.getAccountName()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        _ID, ACCOUNT_TABLE_NAME, ACCOUNT_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(account.getAccountName())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(ACCOUNT_TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

}
