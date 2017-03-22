package com.phoenix.soft.agenda.repos;

import android.util.ArrayMap;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.phoenix.soft.agenda.MainApplication;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.firebase.AccountFire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by yaoda on 20/03/17.
 */

public class FireAccountRepository implements AccountRepository {
    private static final String TAG = "FirebaseRepo";
    @Inject
    @Named("Account")
    DatabaseReference dbRef;
    ArrayMap<String, AccountFire> accountMap = new ArrayMap<>(5);
    private ChildEventListener listener;

    public FireAccountRepository() {
        MainApplication.getFirebaseComponent().inject(this);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                AccountFire accountFire = dataSnapshot.getValue(AccountFire.class);
                accountFire.setKey(dataSnapshot.getKey());
                accountMap.put(dataSnapshot.getKey(), accountFire);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                AccountFire accountFire = dataSnapshot.getValue(AccountFire.class);
                accountFire.setKey(dataSnapshot.getKey());
                accountMap.setValueAt(accountMap.indexOfKey(dataSnapshot.getKey()), accountFire);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (accountMap.containsKey(dataSnapshot.getKey())) {
                    accountMap.remove(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                end();
            }
        };
    }

    @Override
    public List<Account> getAccountList() {
        Query account = dbRef.child("account").limitToFirst(10);
        account.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    AccountFire accountFire = data.getValue(AccountFire.class);
                    accountFire.setKey(data.getKey());
                    accountMap.put(data.getKey(), accountFire);
                }
                Log.d(TAG, "onDataChange: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        List<Account> accountList = new ArrayList<>(accountMap.size());
        for (AccountFire accountFire : accountMap.values()) {
            Account e = accountFire.toAccount();
            e.setTransactionList(Collections.emptyList());
            accountList.add(e);
        }
        return accountList;
    }

    @Override
    public boolean addAccount(Account account) {
        String key = dbRef.child("account").push().getKey();
        account.setKey(key);
        return dbRef.child("account").child(key).setValue(account.toAccountFire()).isSuccessful();
    }

    @Override
    public boolean delAccount(Account account) {
        Task<Void> task = dbRef.child("account").child(account.getKey()).removeValue();
        return task.isSuccessful();
    }

    @Override
    public Account getRandomAccount() {
        return null;
    }

    @Override
    public void start() {
        //dbRef.child("account").addChildEventListener(listener);

    }

    @Override
    public void end() {

        //dbRef.child("account").removeEventListener(listener);
    }
}
