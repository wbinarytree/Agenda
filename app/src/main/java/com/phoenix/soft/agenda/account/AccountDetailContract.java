package com.phoenix.soft.agenda.account;

import com.phoenix.soft.agenda.BaseContract;

/**
 * Created by yaoda on 22/03/17.
 */

public interface AccountDetailContract {
    interface View{
        void showPieChart();
        void showWeekDeatail();
    }
    interface Presneter extends BaseContract.BasePresenter{
        void loadDetail();
    }
}