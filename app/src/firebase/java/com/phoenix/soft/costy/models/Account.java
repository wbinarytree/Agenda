/*
 * Copyright 2017 WBinaryTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phoenix.soft.costy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.costy.models.firebase.AccountFire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Created by yaoda on 22/02/17.
 */
@IgnoreExtraProperties public class Account implements Parcelable, Serializable {
    @Exclude public static final Parcelable.Creator<Account> CREATOR =
        new Parcelable.Creator<Account>() {
            @Override public Account createFromParcel(Parcel source) {
                return new Account(source);
            }

            @Override public Account[] newArray(int size) {
                return new Account[size];
            }
        };
    private CurrencyUnit currency;
    private String accountName;
    private String accountPicUrl;
    private long accountID;
    @Exclude private List<Transaction> transactionList;
    @Exclude private Money income;
    @Exclude private Money outcome;
    @Exclude private String key;

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

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyUnit currency) {
        this.currency = currency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    @Exclude @Override public int describeContents() {
        return 0;
    }

    @Exclude @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountName);
        dest.writeString(this.accountPicUrl);
        dest.writeLong(this.accountID);
        dest.writeList(this.transactionList);
        dest.writeSerializable(this.income);
        dest.writeSerializable(this.outcome);
    }

    public AccountFire toAccountFire() {
        //accountFire.setTransactionList();
        AccountFire accountFire =
            new AccountFire(getAccountName(), getAccountPicUrl(), getAccountID(),
                getIncome().toString(), getOutcome().toString(), getCurrency().toString());
        accountFire.setKey(getKey());
        return accountFire;
    }

    public AccountFire toFire() {
        return this.toAccountFire();
    }
}
