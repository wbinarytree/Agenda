package com.phoenix.soft.costy.module.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.costy.module.Transaction;

import org.joda.money.Money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaoda on 17/03/17.
 */
@IgnoreExtraProperties
public class TransactionFire{
    //    new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
    private static final SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
    private Date date;
    private String money;
    private String desc;
    private String title;
    @Exclude
    private String key;

    //for firebase
    public TransactionFire() {

    }

    public TransactionFire(String date, String money, String desc, String title) {

        try {
            this.date = parser.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.money = money;
        this.desc = desc;
        this.title = title;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(String date) {
        try {
            this.date = parser.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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


    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public Transaction toModule() {
        Transaction transaction = null;
        try {
            transaction = toTransaction();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public Transaction toTransaction() throws ParseException {
        Transaction transaction = new Transaction();
        transaction.setDate(this.date);
        transaction.setDesc(getDesc());
        transaction.setTitle(getTitle());
        transaction.setMoney(Money.parse(getMoney()));
        transaction.setKey(getKey());
        return transaction;
    }
}
