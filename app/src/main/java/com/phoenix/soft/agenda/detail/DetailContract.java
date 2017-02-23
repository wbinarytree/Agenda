package com.phoenix.soft.agenda.detail;

import com.phoenix.soft.agenda.module.Detail;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */

public interface DetailContract {
    interface View {
        void showDetailList(List<Detail> detailList);
        void showNoDetail();
        void showError();
    }

    interface Presenter {
        void loadDetailList();
    }
}
