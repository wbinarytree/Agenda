package com.phoenix.soft.agenda.repos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.module.firebase.TransactionFire;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by yaoda on 22/03/17.
 */

public class FirebaseRxTransactionRepository implements RxTransactionRepository {
    private String accountId;
    private DatabaseReference dbRef;
    private int size;

    public FirebaseRxTransactionRepository(String accountId, DatabaseReference dbRef) {
        this.accountId = accountId;
        this.dbRef = dbRef;
        this.size = 3;
    }

    @Override
    public Maybe<List<Transaction>> getTransactionList() {
        return RxDatabase.queryOnce(dbRef.child("transaction").child(accountId).startAt(size).limitToFirst(20))
                         .map(dataSnapshot -> {
                             List<Transaction> transactions = new ArrayList<>();
                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                 TransactionFire value = snapshot.getValue(TransactionFire.class);
                                 value.setKey(snapshot.getKey());
                                 transactions.add(value.toTransaction());
                             }
                             size += transactions.size();
                             return transactions;
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
