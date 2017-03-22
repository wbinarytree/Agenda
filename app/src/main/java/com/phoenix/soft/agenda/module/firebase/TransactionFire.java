package com.phoenix.soft.agenda.module.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.phoenix.soft.agenda.module.Transaction;

import org.joda.money.Money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaoda on 17/03/17.
 */
@IgnoreExtraProperties
public class TransactionFire implements FirebaseKey {
    private String date;
    private String money;
    private String desc;
    private String title;
    @Exclude
    private String key;

    public TransactionFire() {

    }

    public TransactionFire(String date, String money, String desc, String title) {

        this.date = date;
        this.money = money;
        this.desc = desc;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public Transaction toTransaction() throws ParseException {
        Transaction transaction = new Transaction();
        SimpleDateFormat parser=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = parser.parse(getDate());
        transaction.setDate(date);
        transaction.setDesc(getDesc());
        transaction.setTitle(getTitle());
        transaction.setMoney(Money.parse(getMoney()));
        transaction.setKey(getKey());
        return transaction;
    }
}
