package com.phoenix.soft.agenda.ui.coststream;

import com.phoenix.soft.agenda.module.Account;

import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public interface CostStreamContract {
    interface View {
        void showAccountList(List<Account> accounts);

        void showNoAccount();

        void showError();

        void showDetails();
    }

    interface Presenter {
        void loadAccount();

        void addAccount();

        void modifyAccount();

        void deleteAccount();

    }
}
