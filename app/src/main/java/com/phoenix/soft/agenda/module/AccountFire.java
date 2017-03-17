package com.phoenix.soft.agenda.module;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.joda.money.Money;

/**
 * Created by phoenix on 2017/3/17.
 */
@IgnoreExtraProperties
public class AccountFire {

    private String accountName;
    private String accountPicUrl;
    private long accountID;

    public AccountFire(String accountName, String accountPicUrl, long accountID, Money income, Money outcome) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
        this.income = income;
        this.outcome = outcome;
    }

    private Money income;
    private Money outcome;


    public Money getIncome() {
        return income;
    }

    public void setIncome(Money income) {
        this.income = income;
    }

    public Money getOutcome() {
        return outcome;
    }

    public void setOutcome(Money outcome) {
        this.outcome = outcome;
    }

    public AccountFire() {
    }

    public AccountFire(String accountName, String accountPicUrl, long accountID) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
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
