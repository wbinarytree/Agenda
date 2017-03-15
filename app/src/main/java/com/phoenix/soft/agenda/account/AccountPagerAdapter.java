package com.phoenix.soft.agenda.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.detail.DetailFragment;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.AccountRepository;

import java.util.List;

/**
 * Created by yaoda on 15/03/17.
 */

public class AccountPagerAdapter extends FragmentStatePagerAdapter {
    private List<Account> accounts;
    private DetailFragment currentItem;

    public AccountPagerAdapter(FragmentManager fm, AccountRepository repository) {
        super(fm);
        accounts = repository.getAccountList();
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(accounts.get(position));
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return accounts.get(position).getAccountName();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentItem() != object) {
            currentItem = (DetailFragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public DetailFragment getCurrentItem() {
        return currentItem;
    }
}
