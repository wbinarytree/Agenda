package com.phoenix.soft.agenda.detail;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */

public interface DetailContract {
    interface View {
        void showDetailList(Account account);
        void showAddDetailDialog();
        void updateList();
        void showNoDetail();
        void showError(String errorMessage);
    }

    interface Presenter {
        void loadDetailList();
        void addDetail(String number, boolean add);
    }
}