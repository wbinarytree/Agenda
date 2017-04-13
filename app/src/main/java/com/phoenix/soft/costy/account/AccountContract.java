package com.phoenix.soft.costy.account;

import com.phoenix.soft.costy.BaseContract;
import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.models.Transaction;
import com.phoenix.soft.costy.repos.source.EventType;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface AccountContract   {
    interface View extends BaseContract.BaseView {
        void showAccountList();

        void showNoAccount();

        void showError();

        void update();

        void updateAccount(Account account, EventType updateType);


        void showModifyAccount();

        void showLoading();

        void hideLoading();

        void initAccountList(List<Account> accounts);
    }

    interface Presenter  extends BaseContract.BasePresenter<View>{
        void loadAccount();

        void addAccount(Account account);

        void modifyAccount();

        void deleteAccount(Account account);

        void updateTransactionToAccount(Transaction transaction, Account account);
    }
}
