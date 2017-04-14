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

package com.phoenix.soft.costy.account;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.phoenix.soft.costy.models.Account;
import com.phoenix.soft.costy.transaction.TransactionFragment;

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
