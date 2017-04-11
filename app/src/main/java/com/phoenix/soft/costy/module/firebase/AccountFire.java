package com.phoenix.soft.costy.module.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.costy.module.Account;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.List;

/**
 * Created by phoenix on 2017/3/17.
 */
@IgnoreExtraProperties
public class AccountFire  {

    @Exclude
    private String key;
    private String accountName;
    private String accountPicUrl;
    private long accountID;
    private String income;
    private String outcome;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Exclude
    private List<TransactionFire> detailList;

    public AccountFire(String accountName, String accountPicUrl, long accountID, String income, String outcome,String currency) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
        this.income = income;
        this.outcome = outcome;
        this.currency = currency;
    }

    public AccountFire() {
    }

    public AccountFire(String accountName, String accountPicUrl, long accountID) {
        this.accountName = accountName;
        this.accountPicUrl = accountPicUrl;
        this.accountID = accountID;
    }


    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public Account toModule() {
        return toAccount();
    }

    @Exclude
    public Account toAccount() {
        Account account = new Account();
        account.setAccountPicUrl(getAccountPicUrl());
        account.setAccountID(getAccountID());
        account.setAccountName(getAccountName());
        account.setIncome(Money.parse(getIncome()));
        account.setOutcome(Money.parse(getOutcome()));
        account.setKey(getKey());
        account.setCurrency(CurrencyUnit.of(currency));
        // account.setTransactionList();
        return account;
    }

    public List<TransactionFire> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TransactionFire> detailList) {
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
