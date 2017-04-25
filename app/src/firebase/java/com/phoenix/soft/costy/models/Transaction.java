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
import com.phoenix.soft.costy.models.firebase.TransactionFire;
import java.io.Serializable;
import java.util.Date;
import org.joda.money.Money;

/**
 * Created by yaoda on 22/02/17.
 */

public class Transaction implements Serializable, Parcelable {
    private long id;
    private Date date;
    private Money money;
    private String desc;
    private String title;
    private String key;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeSerializable(this.money);
        dest.writeString(this.desc);
        dest.writeString(this.title);
    }

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        this.id = in.readLong();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.money = (Money) in.readSerializable();
        this.desc = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override public Transaction createFromParcel(Parcel source) {
            return new Transaction(source);
        }

        @Override public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public TransactionFire toTransactionFire() {
        TransactionFire transactionFire =
            new TransactionFire(getDate().toString(), getMoney().toString(), getDesc(), getTitle());
        transactionFire.setKey(getKey());
        return transactionFire;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TransactionFire toFire() {
        return this.toTransactionFire();
    }
}
