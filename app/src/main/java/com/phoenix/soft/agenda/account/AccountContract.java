package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.BaseContract;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Transaction;
import com.phoenix.soft.agenda.repos.source.EventType;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface AccountContract   {
    interface View extends BaseContract.BaseView {
        void showAccountList(List<Account> accounts);

        void showNoAccount();

        void showError();

        void update();

        void updateAccount(Account account, EventType updateType);


        void showModifyAccount();

        void showLoading();

        void hideLoading();
    }

    interface Presenter  extends BaseContract.BasePresenter<View>{
        void loadAccount();

        void addAccount(Account account);

        void modifyAccount();

        void deleteAccount(Account account);

        void updateTransactionToAccount(Transaction transaction, Account account);
    }
}
