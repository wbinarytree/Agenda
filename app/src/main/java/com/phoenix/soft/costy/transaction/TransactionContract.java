package com.phoenix.soft.costy.transaction;

import com.phoenix.soft.costy.BaseContract;
import com.phoenix.soft.costy.module.Transaction;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */

public interface TransactionContract {
    interface View extends BaseContract.BaseView {
        void showTransactionList();
        void showAddDetailDialog();
        void updateList();
        void showNoTransaction();
        void showError(String errorMessage);
        void initTransactionList(List<Transaction> transactions);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void loadDetailList();

        void addDetail(Transaction transaction);
    }
}
