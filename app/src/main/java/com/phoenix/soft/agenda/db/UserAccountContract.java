package com.phoenix.soft.agenda.db;

import android.provider.BaseColumns;

/**
 * Created by yaoda on 27/02/17.
 */

public final class UserAccountContract {
    private UserAccountContract() {}
    public static class AccountEntry implements BaseColumns {
        public static final String TABLE_NAME = "account";
        public static final String NAME = "name";
        public static final String PIC_URL = "pic_url";
        public static final String INCOME = "income";
        public static final String OUTCOME = "outcome";



    }

}
