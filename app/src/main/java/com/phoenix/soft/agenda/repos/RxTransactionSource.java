package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by yaoda on 21/03/17.
 */

public interface RxTransactionSource {
    Maybe<Transaction> getTransaction(String key);

    Maybe<List<Transaction>> getTransactionList();

    Maybe<List<Transaction>> getTransactionListFrom(String key,int num);

    boolean addTransaction(Transaction transaction);

    boolean deleteTransaction(Transaction transaction);

    boolean updateTransaction(Transaction transaction);

    void refreshTransaction();

}
