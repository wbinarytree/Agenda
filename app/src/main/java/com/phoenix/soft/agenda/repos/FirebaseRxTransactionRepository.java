package com.phoenix.soft.agenda.repos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.module.firebase.TransactionFire;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by yaoda on 22/03/17.
 */

public class FirebaseRxTransactionRepository implements RxTransactionRepository {
    private String uid;
    private String accountId;
    private DatabaseReference dbRef;

    public FirebaseRxTransactionRepository(String uid, String accountId,DatabaseReference dbRef) {
        this.uid = uid;
        this.accountId = accountId;
        this.dbRef = dbRef;
    }

    @Override
    public Single<List<Transaction>> getTransactionList() {
        return RxDatabase.queryOnce(dbRef.child(uid)
                                         .child("transaction")
                                         .child(accountId)
                                         .limitToFirst(20))
                         .flatMap(dataSnapshot -> {
                             List<Transaction> transactions = new ArrayList<>();
                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 TransactionFire value = snapshot.getValue(TransactionFire.class);
                                 value.setKey(snapshot.getKey());
                                 transactions.add(value.toTransaction());
                             }
                             return Single.just(transactions);
                         });
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {
        return false;
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        return false;
    }
}