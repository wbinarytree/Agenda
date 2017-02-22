package com.phoenix.soft.agenda.ui.coststream;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.soft.agenda.R;
import com.phoenix.soft.agenda.R2;
import com.phoenix.soft.agenda.module.Account;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yaoda on 22/02/17.
 */

public class AccountListFragment extends Fragment implements CostStreamContract.View {
    @BindView(R2.id.account_list)
    RecyclerView accountList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.list_acc, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void showAccountList(List<Account> accounts) {

    }

    @Override
    public void showNoAccount() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showDetails() {

    }
}
