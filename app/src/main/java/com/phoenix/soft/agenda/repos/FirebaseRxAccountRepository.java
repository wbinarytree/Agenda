package com.phoenix.soft.agenda.repos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by yaoda on 21/03/17.
 */

public class FirebaseRxAccountRepository implements RxAccountRepository {
    @Inject
    @Named("Account")
    DatabaseReference dbRef;

    public FirebaseRxAccountRepository() {
        MainApplication.getFirebaseComponent().inject(this);
    }

    @Override
    public Observable<Account> getAccount() {
        return null;
    }

    @Override
    public Single<List<Account>> getAccountList() {
        return RxDatabase.queryOnce(dbRef.child("account").limitToFirst(10))
                         .flatMap(dataSnapshot -> {
                             List<Account> accounts = new ArrayList<>();
                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 AccountFire value = snapshot.getValue(AccountFire.class);
                                 value.setKey(snapshot.getKey());
                                 accounts.add(value.toAccount());
                             }
                             return Single.just(accounts);
                         });
    }

    @Override
    public boolean addAccount(Account account) {
        DatabaseReference account1 = dbRef.child("account").push();
        account.setKey(account.getKey());
        return account1.setValue(account.toAccountFire()).isSuccessful();
    }

    @Override
    public boolean deleteAccount(Account account) {
        return dbRef.child("account")
                    .child(account.getKey())
                    .removeValue()
                    .isSuccessful();
    }

    @Override
    public boolean updateAccount(Account account) {
        return dbRef.child("account")
                    .child(account.getKey())
                    .setValue(account.toAccountFire())
                    .isSuccessful();
    }

    @Override
    public void start() {
    }

    @Override
    public void end() {

    }
}
