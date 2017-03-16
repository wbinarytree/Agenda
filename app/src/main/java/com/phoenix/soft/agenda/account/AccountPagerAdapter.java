package com.phoenix.soft.agenda.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.detail.DetailFragment;
import com.phoenix.soft.agenda.module.Account;
import com.phoenix.soft.agenda.repos.AccountRepository;

import java.util.List;

/**
 * Created by yaoda on 15/03/17.
 */

public class AccountPagerAdapter extends FragmentPagerAdapter {
    private List<Account> accounts;
    private SparseArray<DetailFragment> detailFragments;

    public AccountPagerAdapter(FragmentManager fm, AccountRepository repository) {
        super(fm);
        accounts = repository.getAccountList();
        detailFragments = new SparseArray<>();
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
    public Object instantiateItem(ViewGroup container, int position) {
        DetailFragment o = (DetailFragment) super.instantiateItem(container, position);
        detailFragments.put(position, o);
        return o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        detailFragments.remove(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return accounts.get(position).getAccountName();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public DetailFragment getFragment(int position) {
        return detailFragments.get(position);
    }
}
