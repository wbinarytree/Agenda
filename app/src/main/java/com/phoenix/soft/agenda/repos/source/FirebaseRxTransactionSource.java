package com.phoenix.soft.agenda.repos.source;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.module.firebase.TransactionFire;
import com.phoenix.soft.agenda.repos.RxTransactionSource;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.List;

import io.reactivex.Maybe;

import static com.phoenix.soft.agenda.rxfirebase.RxDatabase.fireParser;
import static com.phoenix.soft.agenda.rxfirebase.RxDatabase.fireParserList;


/**
 * Created by awang on 2017/3/25.
 */

public class FirebaseRxTransactionSource implements RxTransactionSource {
    private final static String TRANSACTION = "transaction";
    private final static int queryNum = 10;
    private final DatabaseReference dbRef;

    public FirebaseRxTransactionSource(DatabaseReference dbRef) {
        this.dbRef = dbRef.child(TRANSACTION);
    }

    @Override
    public Maybe<Transaction> getTransaction(String key) {
        return RxDatabase.queryOnce(dbRef.equalTo(key))
                         .map(dataSnapshot -> fireParser(dataSnapshot, TransactionFire.class));
    }

    @Override
    public Maybe<List<Transaction>> getTransactionList() {
        return RxDatabase.queryOnce(dbRef.limitToFirst(queryNum))
                         .map((dataSnapshot) -> fireParserList(dataSnapshot, TransactionFire.class));
    }


    @Override
    public Maybe<List<Transaction>> getTransactionListFrom(String key, int num) {
        return RxDatabase.queryOnce(dbRef.orderByKey().endAt(key).limitToFirst(num))
                         .map(dataSnapshot -> fireParserList(dataSnapshot, TransactionFire.class));
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        DatabaseReference db = dbRef.push();
        transaction.setKey(db.getKey());
        return db.setValue(transaction.toTransactionFire()).isSuccessful();
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {
        return dbRef.child(transaction.getKey()).removeValue().isSuccessful();
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        return dbRef.child(transaction.getKey()).setValue(transaction).isSuccessful();
    }

    @Override
    public void refreshTransaction() {

    }

}
