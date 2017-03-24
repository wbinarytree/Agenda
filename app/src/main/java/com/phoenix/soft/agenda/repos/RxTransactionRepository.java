package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by yaoda on 22/03/17.
 */

public interface RxTransactionRepository {
    Maybe<List<Transaction>> getTransactionList();
    boolean addTransaction(Transaction transaction);
    boolean deleteTransaction(Transaction transaction);
    boolean updateTransaction(Transaction transaction);
}
