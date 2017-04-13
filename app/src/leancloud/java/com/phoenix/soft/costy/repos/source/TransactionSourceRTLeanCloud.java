package com.phoenix.soft.costy.repos.source;

import android.util.ArrayMap;

import com.phoenix.soft.costy.models.Transaction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 05/04/17.
 */

public class TransactionSourceRTLeanCloud implements TransactionSourceRT {
    private List<Transaction> actual;{
        actual = new ArrayList<>();
        actual.add(new Transaction());
    }
    private ArrayMap<String, Transaction> transactionMap;
    private Observable<ValueEvent<Transaction>> transactionUpdate;
    private Observable<List<Transaction>> transListObservable;


    public TransactionSourceRTLeanCloud() {
        transactionMap = new ArrayMap<>();
    }


    @Override
    public Observable<List<Transaction>> getTransactionList() {
        return Observable.just(actual);
    }

    @Override
    public Observable<ValueEvent<Transaction>> getTransactionUpdate() {
        return Observable.just(new ValueEvent<>(new Transaction(), EventType.TYPE_ADD));
    }

    @Override
    public Maybe<Transaction> getTransaction(String id) {
        return null;
    }

    @Override
    public Observable<Boolean> addTransaction(Transaction transaction) {
        return Observable.just(true);
    }

    @Override
    public Observable<Boolean> updateTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Observable<Boolean> deleteTransaction(Transaction transaction) {
        return null;
    }
}
