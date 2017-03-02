package com.phoenix.soft.agenda.detail;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import java.util.List;

/**
 * Created by yaoda on 23/02/17.
 */
public class DetailPresenter implements DetailContract.Presenter {

    private final List<Detail> details;
    private final DetailContract.View view;
    private Account account;

    public DetailPresenter(Account account, DetailContract.View view) {
        this.account = account;
        this.details = account.getDetailList();
        this.view = view;
    }

    @Override
    public void loadDetailList() {
        view.showDetailList(details);
    }
}
