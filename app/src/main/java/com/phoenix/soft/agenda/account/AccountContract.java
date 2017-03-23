package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface AccountContract {
    interface View {
        void showAccountList(List<Account> accounts);

        void showNoAccount();

        void showError();

        void updateAccount();

        void showModifyAccount();

        void showLoading();

        void hideLoading();
    }

    interface Presenter {
        void loadAccount();

        void addAccount(Account account);

        void modifyAccount();

        void deleteAccount(Account account);

    }
}
