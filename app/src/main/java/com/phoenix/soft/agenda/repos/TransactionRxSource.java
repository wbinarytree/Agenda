package com.phoenix.soft.agenda.repos;

import com.google.firebase.database.DatabaseReference;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.module.firebase.TransactionFire;
import com.phoenix.soft.agenda.rxfirebase.RxDatabase;

import java.util.List;

import io.reactivex.Maybe;

import static com.phoenix.soft.agenda.rxfirebase.RxDatabase.fireParser;
import static com.phoenix.soft.agenda.rxfirebase.RxDatabase.fireParserList;

/**
 * Created by awang on 2017/3/26.
 */

public class TransactionRxSource implements RxSource<Transaction> {
    private final static String TRANSACTION = "transaction";
    private final DatabaseReference dbRef;
    private final static int queryNum = 10;

    public TransactionRxSource(String key,DatabaseReference dbRef) {
        this.dbRef = dbRef.child(key).child(TRANSACTION);
    }

    @Override
    public Maybe<Transaction> getValue(String key) {
        return RxDatabase.queryOnce(dbRef.equalTo(key))
                         .map(dataSnapshot -> fireParser(dataSnapshot, TransactionFire.class));
    }

    @Override
    public Maybe<List<Transaction>> getValueList() {
        return RxDatabase.queryOnce(dbRef.limitToFirst(queryNum))
                         .map((dataSnapshot) -> fireParserList(dataSnapshot, TransactionFire.class));
    }

    @Override
    public Maybe<List<Transaction>> getValueListFrom(String key, int num) {
        return RxDatabase.queryOnce(dbRef.orderByKey().endAt(key).limitToFirst(num))
                         .map(dataSnapshot -> fireParserList(dataSnapshot, TransactionFire.class));
    }

    @Override
    public boolean addValue(Transaction transaction) {
        DatabaseReference db = dbRef.push();
        transaction.setKey(db.getKey());
        return db.setValue(transaction.toTransactionFire()).isSuccessful();
    }

    @Override
    public boolean deleteValue(Transaction transaction) {
        return dbRef.child(transaction.getKey()).removeValue().isSuccessful();
    }

    @Override
    public boolean updateValue(Transaction transaction) {
        return dbRef.child(transaction.getKey()).setValue(transaction).isSuccessful();
    }

    @Override
    public void refreshTransaction() {

    }
}
