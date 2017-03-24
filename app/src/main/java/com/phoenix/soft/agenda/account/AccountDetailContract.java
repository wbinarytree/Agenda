package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.BaseContract;
import com.phoenix.soft.agenda.module.Account;

/**
 * Created by yaoda on 22/03/17.
 */

public interface AccountDetailContract {
    interface View {
        void showPieChart();

        void showWeekDeatail();

        void selectChart(int position);

        void showNoChart();

        void showError();

        void updateChart(Account account);
    }

    interface Presneter extends BaseContract.BasePresenter {
        void loadDetail();
    }
}
