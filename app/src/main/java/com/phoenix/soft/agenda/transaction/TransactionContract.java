package com.phoenix.soft.agenda.transaction;

import com.phoenix.soft.agenda.module.Transaction;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */

public interface TransactionContract {
    interface View {
        void showTransactionList(List<Transaction> transactions);
        void showAddDetailDialog();
        void updateList();
        void showNoTransaction();
        void showError(String errorMessage);
    }

    interface Presenter {
        void loadDetailList();
        void addDetail(String number, boolean add);
    }
}
