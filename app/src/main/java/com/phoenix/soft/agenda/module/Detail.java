package com.phoenix.soft.agenda.module;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.money.Money;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yaoda on 22/02/17.
 */

public class Detail implements Serializable, Parcelable {
    private String detailId;
    private Date date;
    private Money money;
    private String desc;
    private String title;

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.detailId);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeSerializable(this.money);
        dest.writeString(this.desc);
        dest.writeString(this.title);
    }

    public Detail() {
    }

    protected Detail(Parcel in) {
        this.detailId = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.money = (Money) in.readSerializable();
        this.desc = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}
