package com.phoenix.soft.costy.account;

import com.phoenix.soft.costy.BaseContract;
import com.phoenix.soft.costy.models.Account;

/**
 * Created by yaoda on 22/03/17.
 */

public interface AccountDetailContract {
    interface View {
        void showPieChart();

        void showWeeklyDetail();

        void selectChart(int position);

        void showNoChart();

        void showError();

        void updateAccountChart(Account account);

        void addAccountToChart(Account account);

        void deleteAccountToChart(Account account);
    }

    interface Presneter extends BaseContract.BasePresenter {
        void loadDetail();
    }
}
