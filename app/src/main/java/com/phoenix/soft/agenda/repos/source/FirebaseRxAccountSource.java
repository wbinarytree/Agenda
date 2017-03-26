package com.phoenix.soft.agenda.repos.source;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;
import com.phoenix.soft.agenda.repos.RxAccountSource;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by yaoda on 21/03/17.
 */

public class FirebaseRxAccountSource implements RxAccountSource {
    private DatabaseReference dbRef;
    private int position = 0;
    private static final String ACCOUNT = "account";

    public FirebaseRxAccountSource(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(ACCOUNT);
    }

    @Override
    public Maybe<Account> getAccount(String key) {
        return RxDatabase.queryOnce(dbRef.equalTo(key)).map(dataSnapshot -> {
            AccountFire accountFire = dataSnapshot.getValue(AccountFire.class);
            accountFire.setKey(dataSnapshot.getKey());
            return accountFire.toAccount();
        });
    }

    @Override
    public Maybe<List<Account>> getAccountList() {
        return RxDatabase.queryOnce(dbRef.limitToFirst(10)).map(dataSnapshot -> {
            List<Account> accounts = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                AccountFire value = snapshot.getValue(AccountFire.class);
                value.setKey(snapshot.getKey());
                accounts.add(value.toAccount());
            }
            position += accounts.size();
            return accounts;
        });
    }

    @Override
    public Maybe<List<Account>> getAccountListFrom(String key, int num) {
        return RxDatabase.queryOnce(dbRef.orderByKey().endAt(key).limitToFirst(num))
                         .map(dataSnapshot -> {
                             List<Account> accounts = new ArrayList<>();
                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 AccountFire value = snapshot.getValue(AccountFire.class);
                                 value.setKey(snapshot.getKey());
                                 accounts.add(value.toAccount());
                             }
                             position += accounts.size();
                             return accounts;
                         });
    }

    @Override
    public boolean addAccount(Account account) {
        DatabaseReference account1 = dbRef.push();
        account.setKey(account1.getKey());
        return account1.setValue(account.toAccountFire()).isSuccessful();
    }

    @Override
    public boolean deleteAccount(Account account) {
        return dbRef.child(account.getKey()).removeValue().isSuccessful();
    }

    @Override
    public boolean updateAccount(Account account) {
        return dbRef.child(account.getKey()).setValue(account.toAccountFire()).isSuccessful();
    }

    @Override
    public void refreshAccount() {
        position = 0;
    }

}
