package com.phoenix.soft.agenda.detail;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        view.showDetailList(account);
    }

    @Override
    public void addDetail(String number, boolean add) {
        // TODO: 03/03/17 currency selector
        Money money = add?Money.parse("USD " + number):Money.parse("USD " + "-" + number);
        Detail detail = new Detail();
        detail.setMoney(money);
        detail.setDate(new Date());
        detail.setDesc("temp desc");
        details.add(0,detail);
        view.updateList();
    }
}
