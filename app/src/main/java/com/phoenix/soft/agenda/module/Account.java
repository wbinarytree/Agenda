package com.phoenix.soft.agenda.module;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.agenda.module.firebase.AccountFire;
import com.phoenix.soft.agenda.rxfirebase.FirebaseModule;

import org.joda.money.Money;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoda on 22/02/17.
 */
@IgnoreExtraProperties
public class Account implements Parcelable,Serializable,FirebaseModule.ToFire<AccountFire,Account> {
    @Exclude
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
    private long accountID;
    @Exclude
    private List<Transaction> transactionList;
    @Exclude
    private Money income;
    @Exclude
    private Money outcome;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    private String key;

    public Account() {
    }

    protected Account(Parcel in) {
        this.accountName = in.readString();
        this.accountPicUrl = in.readString();
        this.accountID = in.readLong();
        this.transactionList = new ArrayList<>();
        in.readList(this.transactionList, Transaction.class.getClassLoader());
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

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public List<Transaction> getTransactionList() {
        return this.transactionList;
    }

    public void setTransactionList(@NonNull List<Transaction> transactionList) {
        this.transactionList = transactionList;
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
    @Exclude
    @Override
    public int describeContents() {
        return 0;
    }
    @Exclude
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.accountPicUrl);
        dest.writeLong(this.accountID);
        dest.writeList(this.transactionList);
        dest.writeSerializable(this.income);
        dest.writeSerializable(this.outcome);
    }

    public AccountFire toAccountFire(){
        //accountFire.setTransactionList();
        AccountFire accountFire = new AccountFire(getAccountName(), getAccountPicUrl(), getAccountID(), getIncome()
                .toString(), getOutcome().toString());
        accountFire.setKey(getKey());
        return accountFire;
    }

    @Override
    public AccountFire toFire() {
        return this.toAccountFire();
    }
}
