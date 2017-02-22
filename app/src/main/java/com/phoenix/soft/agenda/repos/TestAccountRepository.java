package com.phoenix.soft.agenda.repos;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import org.joda.money.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by yaoda on 22/02/17.
 */
public class TestAccountRepository implements AccountRepository {
    @Override
    public List<Account> getAccountList() {
        String[] names = {"CashAccount", "BankAccount", "RestaurantAccount"};
        List<Account> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            Account account = new Account();
            account.setAccountName(names[i]);
            account.setAccountID(String.valueOf(i));
            ArrayList<Detail> details = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Detail detail = new Detail();
                detail.setMoney(Money.parse("USD " + r.nextDouble()));
                detail.setDate(new Date());
                details.add(detail);
            }
            account.setDetailList(details);
            list.add(account);
        }
        return list;
    }
}
