package com.phoenix.soft.agenda.module;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.money.Money;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */

public class Account implements Parcelable,Serializable {
    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
    private String accountName;
    private String accountPicUrl;
    private String accountID;
    private List<Detail> detailList;
    private Money income;
    private Money outcome;

    public Account() {
    }

    protected Account(Parcel in) {
        this.accountName = in.readString();
        this.accountPicUrl = in.readString();
        this.accountID = in.readString();
        this.detailList = new ArrayList<>();
        in.readList(this.detailList, Detail.class.getClassLoader());
        this.income = (Money) in.readSerializable();
        this.outcome = (Money) in.readSerializable();
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

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.accountPicUrl);
        dest.writeString(this.accountID);
        dest.writeList(this.detailList);
        dest.writeSerializable(this.income);
        dest.writeSerializable(this.outcome);
    }
}
