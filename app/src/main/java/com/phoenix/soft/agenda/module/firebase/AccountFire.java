package com.phoenix.soft.agenda.module.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.agenda.module.Account;

import org.joda.money.Money;

import java.util.List;

/**
 * Created by phoenix on 2017/3/17.
 */
@IgnoreExtraProperties
public class AccountFire {

    private String accountName;
    private String accountPicUrl;
    private long accountID;
    private String income;
    private String outcome;
    private List<DetailFire> detailList;

    public AccountFire(String accountName, String accountPicUrl, long accountID, String income, String outcome) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
        this.income = income;
        this.outcome = outcome;
    }

    public AccountFire() {
    }

    public AccountFire(String accountName, String accountPicUrl, long accountID) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
    }

    @Exclude
    public Account toAccount() {
        Account account = new Account();
        account.setAccountPicUrl(getAccountPicUrl());
        account.setAccountID(getAccountID());
        account.setAccountName(getAccountName());
        account.setIncome(Money.parse(getIncome()));
        account.setOutcome(Money.parse(getOutcome()));
        // account.setDetailList();
        return account;
    }

    public List<DetailFire> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailFire> detailList) {
        this.detailList = detailList;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getAccountName() {

        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPicUrl() {
        return accountPicUrl;
    }

    public void setAccountPicUrl(String accountPicUrl) {
        this.accountPicUrl = accountPicUrl;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }
}