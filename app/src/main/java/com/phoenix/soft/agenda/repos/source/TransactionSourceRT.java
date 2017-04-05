package com.phoenix.soft.agenda.repos.source;

import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by yaoda on 05/04/17.
 */

public interface TransactionSourceRT {
    Observable<List<Transaction>> getTransactionList();

    Maybe<Transaction> getTransaction(String id);

    Observable<Boolean> addTransaction(Transaction transaction);

    Observable<Boolean> updateTransaction(Transaction transaction);

    Observable<Boolean> deleteTransaction(Transaction transaction);
}
