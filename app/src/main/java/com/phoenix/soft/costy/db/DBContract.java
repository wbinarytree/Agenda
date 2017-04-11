package com.phoenix.soft.costy.db;

import android.provider.BaseColumns;

import static com.phoenix.soft.costy.db.DBContract.AccountEntry.ACCOUNT_TABLE_NAME;

/**
 * Created by yaoda on 27/02/17.
 */

public final class DBContract {
    private DBContract() {}
    public static class AccountEntry implements BaseColumns {
        public static final String ACCOUNT_TABLE_NAME = "account";
        public static final String ACCOUNT_NAME = "name";
        public static final String ACCOUNT_PIC_URL = "pic_url";
        public static final String ACCOUNT_INCOME = "income";
        public static final String ACCOUNT_OUTCOME = "outcome";

        public static final String SQL_CREATE_ACCOUNT_TABLE =
                "CREATE TABLE " + ACCOUNT_TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ACCOUNT_NAME + " TEXT," +
                        ACCOUNT_OUTCOME + " TEXT," +
                        ACCOUNT_PIC_URL + " TEXT," +
                        ACCOUNT_INCOME + " TEXT)";

        public static final String SQL_DELETE_ACCOUNT_TABLE =
                "DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME;

    }

    public static class DetailEntry implements BaseColumns{
        public static final String DETAIL_TABLE_NAME = "detail";
        public static final String DETAIL_DATE = "date";
        public static final String DETAIL_MONEY = "money";
        public static final String DETAIL_DESC = "description";
        public static final String DETAIL_TITLE = "title";
        public static final String FK_ACCOUNT_ID = "account_id";

        public static final String SQL_CREATE_DETAIL_TABLE =
                "CREATE TABLE " + DETAIL_TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FK_ACCOUNT_ID + "INTEGER REFERENCES" + ACCOUNT_TABLE_NAME +
                        DETAIL_DATE + " TEXT," +
                        DETAIL_MONEY + " TEXT," +
                        DETAIL_DESC + " TEXT," +
                        DETAIL_TITLE + " TEXT)";

        public static final String SQL_DELETE_DETAIL_TABLE =
                "DROP TABLE IF EXISTS " + DETAIL_TABLE_NAME;
    }


}
