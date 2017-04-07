package com.phoenix.soft.agenda.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.transaction.TransactionFragment;
import com.phoenix.soft.agenda.transaction.TransactionPresenter;

import java.util.List;

/**
 * Created by yaoda on 15/03/17.
 */

public class AccountPagerAdapter extends FragmentPagerAdapter {
    private List<Account> accounts;
    private SparseArray<TransactionFragment> transactionFragments;

    public AccountPagerAdapter(FragmentManager fm, List<Account> accounts) {
        super(fm);
        this.accounts = accounts;
        transactionFragments = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        return TransactionFragment.newInstance(accounts.get(position));
    }

    @Override
    public int getCount() {
        return accounts.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TransactionFragment o = (TransactionFragment) super.instantiateItem(container, position);
        transactionFragments.put(position, o);
        return o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        transactionFragments.remove(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return accounts.get(position).getAccountName();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public TransactionFragment getFragment(int position) {
        return transactionFragments.get(position);
    }
}
