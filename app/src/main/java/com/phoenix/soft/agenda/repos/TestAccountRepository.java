package com.phoenix.soft.agenda.repos;

import android.content.Context;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.db.UserDataHelper;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.module.Detail;

import org.joda.money.Money;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by yaoda on 22/02/17.
 */
public class TestAccountRepository implements AccountRepository {
    private static List<Account> accountList;
    private Context context;
    private File localData;
    private UserDataHelper userDataHelper;

    public TestAccountRepository() {
        if (accountList == null) {
            accountList = createList();
        }
        userDataHelper = new UserDataHelper(context);
        saveDataToDb();
    }

    public TestAccountRepository(Context context) {
        this.context = context;
        if (accountList == null) {
            accountList = createList();
        }
        //userDataHelper = UserDataHelper.getInstance(context);
        //saveDataToDb();
    }


    @Override
    public List<Account> getAccountList() {
        return accountList;
    }

    @Override
    public boolean addAccount(Account account) {
        //userDataHelper.addAccount(account);
        return accountList.add(account);
    }

    @Override
    public boolean delAccount(Account account) {
        return accountList.remove(account);
    }


    @Override
    public Account getRandomAccount() {
        Random r = new Random();
        Account account = new Account();
        account.setAccountName(String.valueOf(r.nextLong()));
        account.setAccountID(r.nextLong());
        account.setIncome(Money.parse("USD " + r.nextInt()));
        account.setOutcome(Money.parse("USD " + r.nextInt()));
        ArrayList<Detail> details = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Detail detail = new Detail();
            detail.setMoney(Money.parse("USD " + r.nextInt()));
            detail.setDate(new Date(Math.abs(System.currentTimeMillis() - r.nextLong())));
            details.add(detail);
        }
        account.setDetailList(details);
        addAccount(account);
        return account;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    private List<Account> createList() {
        String[] names = {"CashAccount", "BankAccount", "RestaurantAccount"};
        String[] urls = {String.valueOf(R.drawable.img_download), String.valueOf(R.drawable.img_download_1), String.valueOf(R.drawable.akti510)};
        List<Account> list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            Account account = new Account();
            account.setAccountName(names[i]);
            account.setAccountID(i);
            account.setAccountPicUrl(urls[i]);
            account.setIncome(Money.parse("USD " + r.nextInt()));
            account.setOutcome(Money.parse("USD " + r.nextInt()));
            ArrayList<Detail> details = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Detail detail = new Detail();
                detail.setMoney(Money.parse("USD " + r.nextInt()));
                detail.setDate(new Date(Math.abs(System.currentTimeMillis() - r.nextLong())));
                details.add(detail);
            }
            account.setDetailList(details);
            list.add(account);
        }
        return list;
    }

    // TODO: 02/03/17 finish save data to local file
    private void saveData() {
        File filesDir = context.getFilesDir();
        final String localPath = context.getString(R.string.local_user_data_account);
        File[] files = filesDir.listFiles((dir, name) -> {
            if (name.equals(localPath)) {
                return true;
            }
            return false;
        });
        if (files.length == 0) {
            localData = new File(context.getFilesDir(), localPath);
        } else {
            localData = files[0];
        }

        try {
            FileOutputStream outputStream = context.openFileOutput(localPath, Context.MODE_PRIVATE);
            outputStream.write("Test".getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveDataToDb() {
        for (Account account : accountList) {
            userDataHelper.addAccount(account);
        }
    }
}
